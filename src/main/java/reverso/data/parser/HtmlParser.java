package reverso.data.parser;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class HtmlParser {
    private final Logger logger = Logger.getLogger(HtmlParser.class.getName());
    private final Properties properties;

    public HtmlParser(Properties properties) {
        this.properties = properties;
    }

    public Map<String, String[]> parseConjugationPage(Connection.Response response){

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

    public Map<String, String> parseContextPage(Document document) {
        Elements elements = document.select(".example");

        Map<String, String> contextMap = new HashMap<>();

        for (Element element : elements) {
            String sourceText = extractTextWithEmphasis(element.select(".src .text").first());
            String targetText = extractTextWithEmphasis(element.select(".trg .text").first());
            contextMap.put(sourceText, targetText);
        }
        return contextMap;
    }

    public Map<String, List<String>> parseSynonymsPage(Connection.Response synonymsResponse) {

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

    private String extractTextWithEmphasis(Element element) {
        StringBuilder sb = new StringBuilder();
        for (Node child : element.childNodes()) {
            if (child instanceof TextNode) {
                sb.append(((TextNode) child).text());
            } else if (child instanceof Element) {
                Element childElement = (Element) child;
                if (childElement.tagName().equals("em")) {
                    sb.append("<b>").append(childElement.html()).append("</b>");
                } else {
                    sb.append(extractTextWithEmphasis(childElement));
                }
            }
        }
        return sb.toString();
    }
    public String[] parseContextPageGetTranslations(Document document) {
        return document.getElementsByClass("display-term")
                .stream()
                .map(Element::text)
                .toArray(String[]::new);
    }
}
