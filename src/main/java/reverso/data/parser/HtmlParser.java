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

    public Map<String, String[]> parseConjugationPage(Connection.Response response) {

        Document document = null;
        try {
            document = response.parse();
        } catch (IOException e) {
            logger.log(Level.SEVERE, properties.getProperty("message.error.common.parsing") +
                    "in the method + parseConjugationPage ");
        }

        String metaDescription = document.getElementById("metaDescription").attr("content");
        String language = metaDescription.split(" ")[2].toLowerCase();

        return switch (language) {
            case "russian", "hebrew", "arabic" ->
                    ConjugationPageParser.parseRussianHebrewArabicConjugation(document);
            case "portuguese" -> ConjugationPageParser.parsePortugueseConjugation(document);
            case "japanese" -> ConjugationPageParser.parseJapaneseConjugation(document);
            default -> ConjugationPageParser.parseOrdinaryConjugation(document);
        };
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

    static class ConjugationPageParser {

        private static Map<String, String[]> parseJapaneseConjugation(Document document) {
            Map<String, String[]> conjugationData = new LinkedHashMap<>();

            Elements blueBoxWraps = document.getElementsByClass("blue-box-wrap");

            for (Element blueBoxWrap : blueBoxWraps) {
                String key = blueBoxWrap.attr("mobile-title");
                List<String> values = new ArrayList<>();

                Element ulElement = blueBoxWrap.selectFirst("ul.wrap-verbs-listing");
                if (ulElement == null) continue;

                for (Element liElement : ulElement.children()) {
                    StringBuilder result = new StringBuilder();
                    Elements iElements = liElement.select("i");

                    for (Element i : iElements) {
                        if (i.hasClass("particletxt")) {
                            result.append(" ").append(i.text());
                        } else if (i.hasClass("verbtxt")) {
                            Element rubySpan = i.selectFirst("span.ruby");
                            if (rubySpan != null) {
                                result.append(rubySpan.attr("value"));
                            } else {
                                result.append(i.text());
                            }
                        }
                    }
                    String finalValue = result.toString().trim();
                    if (!finalValue.isEmpty()) {
                        values.add(finalValue);
                    }
                }
                conjugationData.put(key, values.toArray(new String[0]));
            }
            return conjugationData;
        }

        private static Map<String, String[]> parseRussianHebrewArabicConjugation(Document document) {
            Map<String, String[]> conjugationData = new LinkedHashMap<>();

            Elements blueBoxWraps = document.getElementsByClass("blue-box-wrap");

            for (Element blueBoxWrap : blueBoxWraps) {
                String key = blueBoxWrap.attr("mobile-title");
                List<String> values = new ArrayList<>();
                Element ulElement = blueBoxWrap.selectFirst("ul.wrap-verbs-listing");
                if (ulElement == null) continue;

                for (Element liElement : ulElement.children()) {
                    for (Element div : liElement.select("div:not(.transliteration)")) {
                        StringBuilder result = new StringBuilder();
                        Elements iElements = div.select("i");

                        for (Element i : iElements) {
                            if (i.hasClass("graytxt") || i.hasClass("auxgraytxt")) {
                                result.append(i.text()).append(" ");
                            } else if (i.hasClass("verbtxt") || i.hasClass("verbtxt-term") || i.hasClass("hglhOver")) {
                                result.append(i.text());
                            } else if (i.hasClass("particletxt")) {
                                result.append(" ").append(i.text());
                            }
                        }
                        values.add(result.toString().trim());
                    }
                }
                conjugationData.put(key, values.toArray(new String[0]));
            }
            return conjugationData;
        }


        private static Map<String, String[]> parseOrdinaryConjugation(Document document) {

            Map<String, String[]> conjugationData = new LinkedHashMap<>();

            Elements wordWrapRows = document.getElementsByClass("word-wrap-row");

            for (Element element : wordWrapRows) {
                Elements blueBoxWraps = element.getElementsByClass("blue-box-wrap");
                for (Element blueBoxWrap : blueBoxWraps) {
                    String key = blueBoxWrap.attr("mobile-title");
                    String[] liTexts = blueBoxWrap.selectFirst("ul")
                            .select("li").stream()
                            .map(li -> li.getElementsByTag("i").stream()
                                    .map(Element::wholeText)
                                    .collect(Collectors.joining("")))
                            .toArray(String[]::new);
                    conjugationData.put(key, liTexts);
                }
            }
            return conjugationData;
        }

        private static Map<String, String[]> parsePortugueseConjugation(Document document) {
            Map<String, String[]> conjugationData = new LinkedHashMap<>();

            Elements blueBoxWraps = document.getElementsByClass("blue-box-wrap");

            for (Element blueBoxWrap : blueBoxWraps) {
                String key = blueBoxWrap.attr("mobile-title");
                List<String> values = new ArrayList<>();

                Element ulElement = blueBoxWrap.selectFirst("ul.wrap-verbs-listing");
                if (ulElement == null) continue;

                for (Element liElement : ulElement.children()) {
                    StringBuilder result = new StringBuilder();
                    Elements iElements = liElement.select("i");

                    for (Element i : iElements) {
                        if (i.hasClass("particletxt")) {
                            result.append(i.text()).append(" ");
                        } else if (i.hasClass("graytxt") || i.hasClass("auxgraytxt")) {
                            result.append(i.text()).append(" ");
                        } else if (i.hasClass("verbtxt-term") || i.hasClass("verbtxt-term-irr")) {
                            result.append(i.text());
                        }
                    }
                    values.add(result.toString().trim());
                }
                conjugationData.put(key, values.toArray(new String[0]));
            }
            return conjugationData;
        }
    }
}
