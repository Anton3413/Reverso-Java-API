package reverso.data.parser;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import reverso.Reverso;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class HtmlParser {
    private static final Logger logger = Logger.getLogger(HtmlParser.class.getName());
    private static Properties properties;

    static {
        initializeProperties();
    }

    public static Map<String, String[]> parseConjugationPage(Connection.Response response){

        Elements resultBlock = null;
        try {
            resultBlock = response.parse().getElementsByClass("word-wrap-row");
        } catch (IOException e) {
            logger.log(Level.SEVERE, properties.getProperty("message.error.common.parsing") +
                    "in the method + parseConjugationPage ");
        }
        Map<String, String[]> conjugationData = new LinkedHashMap<>();

        for (Element element : resultBlock) {
            Elements blueBoxWraps = element.getElementsByClass("blue-box-wrap");
            for (Element blueBoxWrap : blueBoxWraps) {
                String key = blueBoxWrap.attr("mobile-title");
                String[] liTexts = blueBoxWrap.selectFirst("ul")
                        .select("li").stream()
                        .map(li -> li.getElementsByTag("i").text())
                        .toArray(String[]::new);
                conjugationData.put(key, liTexts);
            }
        }
        return conjugationData;
    }

    public static Map<String, String> parseContextPage(Document document) {
        Elements elements = document.select(".example");

        Map<String, String> contextMap = new HashMap<>();

        for (Element element : elements) {
            String sourceText = extractTextWithEmphasis(element.select(".src .text").first());
            String targetText = extractTextWithEmphasis(element.select(".trg .text").first());
            contextMap.put(sourceText, targetText);
        }
        return contextMap;
    }

    public static Map<String, List<String>> parseSynonymsPage(Connection.Response synonymsResponse) {

        Elements wrapHoldProps = null;
        try {
            wrapHoldProps = synonymsResponse.parse().select(".wrap-hold-prop");
        } catch (IOException e) {
            logger.log(Level.SEVERE, properties.getProperty("message.error.common.parsing") +
                    "in the method + parseSynonymsPage ");
        }

        Map<String, List<String>> synonymsMap = new LinkedHashMap<>();

        wrapHoldProps.forEach(wrapHoldProp -> {
            String partOfSpeech = wrapHoldProp.selectFirst("div.words-options h2").text();
            List<String> synonyms = wrapHoldProp.select("div.pannel ul.word-box li a")
                    .stream()
                    .map(Element::text)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
            synonymsMap.put(partOfSpeech, synonyms);
        });
        return synonymsMap;
    }

    private static String extractTextWithEmphasis(Element element) {
        StringBuilder sb = new StringBuilder();
        for (Node child : element.childNodes()) {
            if (child instanceof TextNode) {
                sb.append(((TextNode) child).text());
            } else if (child instanceof Element) {
                Element childElement = (Element) child;
                if (childElement.tagName().equals("em")) {
                    sb.append("<em>").append(childElement.html()).append("</em>");
                } else {
                    // Recursively handle nested elements
                    sb.append(extractTextWithEmphasis(childElement));
                }
            }
        }
        return sb.toString();
    }

    static private void initializeProperties(){
        try {
            properties = new Properties();
            properties.load(Reverso.class.getResourceAsStream("/messages.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Problem during properties file initialization. Possibly the path is incorrect");
        }
    }
}
