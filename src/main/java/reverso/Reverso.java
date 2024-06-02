package reverso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import reverso.data.SynonymResponse;
import reverso.supportedLanguages.SynonymLanguage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reverso {

    private static final String SYNONYM_URL = "https://synonyms.reverso.net/synonym/";


    public static SynonymResponse getSynonyms(SynonymLanguage language, String word) throws IOException {

        String URL = SYNONYM_URL + language.toString() + "/" + word + "?group";

        Document document = Jsoup.connect(URL).get();

        SynonymResponse response = new SynonymResponse();
        response.setSourceLanguage(language.toString());
        response.setWord(word);

        Elements wrapHoldProps = document.select(".wrap-hold-prop");

        Map<String, List<String>> synonymsMap = new HashMap<>();


        String partOfSpeech;
        List<String> synonyms;
        for (Element wrapHoldProp : wrapHoldProps) {

            // Находим заголовок внутри "words-options pos"
            Element headerElement = wrapHoldProp.selectFirst("div[class^=words-options pos] > h2");
                partOfSpeech = headerElement.text();

                // Ищем все синонимы внутри "pannel cluster"
                Element wordOpt = wrapHoldProp.selectFirst("div[class^=pannel cluster] > div.word-opt > ul");
                    Elements liElements = wordOpt.select("li");
                    synonyms = new ArrayList<>();
                    for (Element li : liElements) {
                        Element aTag = li.selectFirst("a");
                            synonyms.add(aTag.text());
                    }
                    synonymsMap.put(partOfSpeech, synonyms);
        }
        for (Map.Entry<String, List<String>> entry : synonymsMap.entrySet()) {
            System.out.println("Part of Speech: " + entry.getKey());
            System.out.println("Synonyms: " + entry.getValue());
        }
        response.setSynonyms(synonymsMap);
        return response;
    }


    /*public static void getSynonyms(SynonymLanguage language, String word) throws IOException {

        String URL = SYNONYM_URL + language.toString() + "/" + word + "?group";

        Document document = Jsoup.connect(URL).get();

        SynonymResponse response = new SynonymResponse();
        response.setSourceLanguage(language.toString());
        response.setWord(word);

        Elements wrapHoldProps = document.select(".wrap-hold-prop");

        Map<String, List<String>> synonymsMap = new HashMap<>();


        String partOfSpeech;
        List<String> synonyms;
        for (Element wrapHoldProp : wrapHoldProps) {

            // Находим заголовок внутри "words-options pos"
            Element headerElement = wrapHoldProp.selectFirst("div[class^=words-options pos] > h2");
            if (headerElement != null) {
                partOfSpeech = headerElement.text();

                // Ищем все синонимы внутри "pannel cluster"
                Element wordOpt = wrapHoldProp.selectFirst("div[class^=pannel cluster] > div.word-opt > ul");
                if (wordOpt != null) {
                    Elements liElements = wordOpt.select("li");
                    List<String> synonyms = new ArrayList<>();
                    for (Element li : liElements) {
                        Element aTag = li.selectFirst("a");
                        if (aTag != null) {
                            synonyms.add(aTag.text());
                        }
                    }
                    // Добавляем часть речи и её синонимы в HashMap
                    synonymsMap.put(header, synonyms);
                }
            }
        }
        for (Map.Entry<String, List<String>> entry : synonymsMap.entrySet()) {
            System.out.println("Part of Speech: " + entry.getKey());
            System.out.println("Synonyms: " + entry.getValue());
        }
    }*/
}
