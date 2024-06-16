package reverso;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import reverso.data.response.impl.*;
import reverso.supportedLanguages.Language;
import reverso.supportedLanguages.Voice;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Reverso {
    private static Properties properties;
    private static final String SYNONYM_URL = "https://synonyms.reverso.net/synonym/";
    private static final String CONTEXT_URL = "https://context.reverso.net/translation/";
    private static final String TRANSLATE_URL = "https://api.reverso.net/translate/v1/translation";
    private static final String VOICE_URL = "https://voice.reverso.net/RestPronunciation.svc/v1/output=json/GetVoiceStream/";
    private static final String CONJUGATION_URL = "https://conjugator.reverso.net/conjugation";

    static {
        initializeProperties();
    }
    public static SynonymResponse getSynonyms(Language language, String word){
        if(language.getShortName()==null){
            String errorMessage = properties.getProperty("message.error.synonym.unSupportedLanguage");
            return new SynonymResponse(false,errorMessage, language.getFullName(), word);
        }
        String URL = SYNONYM_URL + language.getShortName() + "/" + word;

        Connection.Response response;
        Elements wrapHoldProps;
        try {
           response= Jsoup.connect(URL)
                   .ignoreHttpErrors(true)
                   .execute();
            wrapHoldProps = response.parse().select(".wrap-hold-prop");
        } catch (IOException e) {
            String errorMessage = properties.getProperty("message.error.connection");
            return new SynonymResponse(false,errorMessage, language.getFullName(), word);
        }
        if(response.statusCode()==404){
            String errorMessage = properties.getProperty("message.error.synonym.noResults");
            return new SynonymResponse(false,errorMessage, language.getFullName(), word);
        }
        Map<String, List<String>> synonymsMap = wrapHoldProps.stream()
                .collect(Collectors.toMap(
                        wrapHoldProp -> wrapHoldProp.selectFirst("div[class^=words-options pos] > h2").text(),
                        wrapHoldProp -> wrapHoldProp.selectFirst("div[class^=pannel cluster] > div.word-opt > ul")
                                .select("li a").stream()
                                .map(Element::text)
                                .collect(Collectors.toList())
                ));
        return new SynonymResponse(true, language.toString(), word, synonymsMap);
    } /*

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
    }*/

    public static VoiceResponse getVoiceStream(Voice voice, String text) {

        String base64Text = Base64.getEncoder().encodeToString(text.getBytes());

        String requestURL = VOICE_URL + "voiceName=" + voice.getName() + "?voiceSpeed=80" + "&" + "inputText=" + base64Text;

        Connection.Response response;
        try {
            response = Jsoup.connect(requestURL)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .execute();
        } catch (IOException e) {
            String errorMessage = properties.getProperty("message.error.connection");
            return new VoiceResponse(false,errorMessage, voice.getLanguage(), text, voice.getName(), voice.getGender());
        }
        if(response.statusCode() == 404) {
            String errorMessage = properties.getProperty("message.error.voiceStream.404");
            return new VoiceResponse(false,errorMessage, voice.getLanguage(), text, voice.getName(), voice.getGender());
        } else if (response.bodyAsBytes().length == 9358) {
            String errorMessage = properties.getProperty("message.error.voiceStream.textTooLong");
            return new VoiceResponse(false,errorMessage, voice.getLanguage(), text, voice.getName(), voice.getGender());
        }
        return new VoiceResponse(true, voice.getLanguage(), text, voice.getName(), voice.getGender(), response.bodyAsBytes());
    }

    /*public static void getWordConjugation(ContextLanguage language, String word) throws IOException {

        String URL = CONJUGATION_URL + "-" + language.toString() + "-" + "verb" + "-" + word + ".html";

        Document document = null;

            Connection.Response response = Jsoup.connect(URL)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .execute();

            if (response.statusCode() == 404) {
                document = response.parse();
                String errorMessage = document.getElementById("ch_lblCustomMessage")
                        .text();
            } else {
                document = Jsoup.parse(response.body());
            }



        Elements resultBlock = document.getElementsByClass("word-wrap-row");

        // Инициализируем мапу для хранения результатов
        Map<String, String[]> conjugationData = new HashMap<>();

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
                        conjugationData.put(key, liTexts);
            }
        }
      *//*  ConjugationResponse response = new ConjugationResponse(true,)*//*

        conjugationData.forEach((key, value) -> {
            System.out.println("Key: " + key);
            for (String text : value) {
                System.out.println("  Value: " + text);
            }
        });
    }*/
     static private void initializeProperties(){
        try {
            properties = new Properties();
            properties.load(Reverso.class.getResourceAsStream("/messages.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Problem during properties file initialization. Possibly the path is incorrect");
        }
    }
}

