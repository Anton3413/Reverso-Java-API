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

        String URL = SYNONYM_URL + language.toString() + "/" + word;

        Document document = Jsoup.connect(URL).get();

        Elements wrapHoldProps = document.select(".wrap-hold-prop");

        if(wrapHoldProps.isEmpty()) {
            return new SynonymResponse(false);
        }

        Map<String, List<String>> synonymsMap = new HashMap<>();

        String partOfSpeech;
        List<String> synonyms;
        for (Element wrapHoldProp : wrapHoldProps) {

            Element headerElement = wrapHoldProp.selectFirst("div[class^=words-options pos] > h2");
                partOfSpeech = headerElement.text();

                Element wordOpt = wrapHoldProp.selectFirst("div[class^=pannel cluster] > div.word-opt > ul");
                    Elements liElements = wordOpt.select("li");
                    synonyms = new ArrayList<>();
                    for (Element li : liElements) {
                        Element aTag = li.selectFirst("a");
                            synonyms.add(aTag.text());
                    }
                    synonymsMap.put(partOfSpeech, synonyms);
        }
      return new SynonymResponse(true, language.toString(), word, synonymsMap);
    }
}
