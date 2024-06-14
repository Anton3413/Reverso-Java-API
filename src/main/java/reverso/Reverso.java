package reverso;

import com.google.gson.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import reverso.data.request.TranslationRequest;
import reverso.data.response.ContextResponse;
import reverso.data.response.SynonymResponse;
import reverso.data.response.TranslateResponse;
import reverso.supportedLanguages.ContextLanguage;
import reverso.supportedLanguages.SynonymLanguage;
import reverso.data.jsonParser.JsonParser;
import reverso.supportedLanguages.Voice;

import java.io.*;
import java.util.*;

public class Reverso {

    private static final String SYNONYM_URL = "https://synonyms.reverso.net/synonym/";
    private static final String CONTEXT_URL = "https://context.reverso.net/translation/";
    private static final String TRANSLATE_URL = "https://api.reverso.net/translate/v1/translation";
    private static final String VOICE_URL = "https://voice.reverso.net/RestPronunciation.svc/v1/output=json/GetVoiceStream/";
    private static final String CONJUGATION_URL = "https://conjugator.reverso.net/conjugation";

    public static SynonymResponse getSynonyms(SynonymLanguage language, String word) throws IOException {

        String URL = SYNONYM_URL + language.toString() + "/" + word;

        Document document = Jsoup.connect(URL).get();

        Elements wrapHoldProps = document.select(".wrap-hold-prop");

        if (wrapHoldProps.isEmpty()) {
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

    public static ContextResponse getContext(ContextLanguage sourceLanguage,
                                             ContextLanguage targetLanguage, String word) {

        Map<String, String> contextMap = new HashMap<>();

        String URL = CONTEXT_URL + sourceLanguage.getName() + "-" + targetLanguage.getName() + "/" + word;

        Document document;
        try {
            document = Jsoup.connect(URL).get();
        } catch (IOException e) {
            return new ContextResponse(false);
        }

        Elements elements = document.select(".example");

        for (Element element : elements) {
            contextMap.put(element.child(0).text(), element.child(1).text());
        }
        if (contextMap.isEmpty()) {
            return new ContextResponse(false);
        }
        return new ContextResponse(true, sourceLanguage.toString(), targetLanguage.toString(), contextMap);
    }

    public static TranslateResponse getTranslations(SynonymLanguage sourceLanguage, SynonymLanguage targetLanguage,
                                                    String text) throws IOException {

        TranslationRequest request = new TranslationRequest(sourceLanguage.toString(), targetLanguage.toString(), text);

        Connection.Response response = Jsoup.connect(TRANSLATE_URL)
                .header("Content-Type", "application/json")
                .requestBody(new Gson().toJson(request))
                .method(Connection.Method.POST)
                .ignoreContentType(true)
                .execute();

        String requestResult = response.body();

        Map<String, String> contextTranslations = JsonParser.getContextTranslations(requestResult);

        System.out.println(requestResult);
        TranslateResponse translateResponse = new Gson().fromJson(requestResult, TranslateResponse.class);
        translateResponse.setContextTranslations(contextTranslations);

        return translateResponse;
    }

    public static byte[] getVoiceStream(Voice voice, String text) throws IOException {
        String base64Text = Base64.getEncoder().encodeToString(text.getBytes());

        String url = VOICE_URL + "voiceName=" + voice.getName() + "?voiceSpeed=80" + "&" + "inputText=" + base64Text;

        Connection.Response response = Jsoup.connect(url)
                .ignoreContentType(true)
                .execute();

        return response.bodyAsBytes();

    }

    public static void getWordConjugation(ContextLanguage language, String word) throws IOException {

        String URL = CONJUGATION_URL + "-" + language.toString() + "-" + "verb" + "-" + word + ".html";

        Document document = Jsoup.connect(URL)
                .ignoreContentType(true)
                .execute()
                .parse();

        Elements resultBlock = document.getElementsByClass("word-wrap-row");

        // Инициализируем мапу для хранения результатов
        Map<String, String[]> resultMap = new HashMap<>();

        // Проходим по каждому элементу с классом "word-wrap-row"
        for (Element element : resultBlock) {
            // Ищем все дочерние элементы с классом "blue-box-wrap", у которых есть атрибут "mobile-title"
            Elements blueBoxWraps = element.getElementsByClass("blue-box-wrap");
            for (Element blueBoxWrap : blueBoxWraps) {
                    // Извлекаем текст атрибута "mobile-title" для ключа
                    String key = blueBoxWrap.attr("mobile-title");

                        // Инициализируем массив для хранения текстов элементов "li"
                        String[] liTexts = blueBoxWrap.selectFirst("ul")
                                .select("li").stream()
                                .map(li -> li.getElementsByTag("i").text())
                                .toArray(String[]::new);
                        // Добавляем ключ и массив текстов в мапу
                        resultMap.put(key, liTexts);
            }
        }
        resultMap.forEach((key, value) -> {
            System.out.println("Key: " + key);
            for (String text : value) {
                System.out.println("  Value: " + text);
            }
        });
    }
}

