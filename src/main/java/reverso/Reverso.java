package reverso;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import reverso.data.jsonParser.JSONParser;
import reverso.data.request.TranslationRequest;
import reverso.data.response.impl.*;
import reverso.supportedLanguages.Language;
import reverso.supportedLanguages.Voice;


import java.io.IOException;
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

    public static SynonymResponse getSynonyms(Language language, String word) {
        if (language.getSynonymName() == null) {
            String errorMessage = properties.getProperty("message.error.synonym.unSupportedLanguage");
            return new SynonymResponse(false, errorMessage, language.getFullName(), word);
        }
        String URL = SYNONYM_URL + language.getSynonymName() + "/" + word;

        Connection.Response response;
        Elements wrapHoldProps;
        try {
            response = Jsoup.connect(URL)
                    .ignoreHttpErrors(true)
                    .execute();
            wrapHoldProps = response.parse().select(".wrap-hold-prop");
        } catch (IOException e) {
            String errorMessage = properties.getProperty("message.error.connection");
            return new SynonymResponse(false, errorMessage, language.getFullName(), word);
        }
        if (response.statusCode() == 404) {
            String errorMessage = properties.getProperty("message.error.synonym.noResults");
            return new SynonymResponse(false, errorMessage, language.getFullName(), word);
        }
        Map<String, List<String>> synonymsMap = new LinkedHashMap<>();
        wrapHoldProps.forEach(wrapHoldProp -> {
            String partOfSpeech = wrapHoldProp.selectFirst("div.words-options h2").text();
            List<String> synonyms = wrapHoldProp.select("div.pannel ul.word-box li a")
                    .stream()
                    .map(Element::text)
                    .filter(s -> !s.isEmpty()) // Filter out empty strings
                    .collect(Collectors.toList());
            synonymsMap.put(partOfSpeech, synonyms);
        });
        if(synonymsMap.isEmpty()) {
            String message = properties.getProperty("message.error.synonym.noResults");
            return new SynonymResponse(false, message, language.getFullName(), word);
        }

        return new SynonymResponse(true, language.getFullName(), word, synonymsMap);
    }

    public static ContextResponse getContext(Language sourceLanguage, Language targetLanguage, String word) {

        ContextResponse contextResponse = new ContextResponse(false,null, sourceLanguage.getFullName(),
                targetLanguage.getFullName(), word);

        if(sourceLanguage.equals(targetLanguage)){
            contextResponse.setErrorMessage(properties.getProperty("message.error.translate.sameLanguage"));
        }

        String URL = CONTEXT_URL + sourceLanguage.getFullName() + "-" + targetLanguage.getFullName() + "/" + word;

        Connection.Response response;
        Elements elements;
        try {
            response = Jsoup.connect(URL).execute();
            elements = response.parse().select(".example");
        } catch (IOException e) {
            contextResponse.setErrorMessage(properties.getProperty("message.error.connection"));
            return contextResponse;
        }
        if(response.statusCode()==304){
            contextResponse.setErrorMessage(properties.getProperty("message.error.context.UnsupportedLanguages"));
        }
        Map<String, String> contextMap = new HashMap<>();

        for (Element element : elements) {
            contextMap.put(element.child(0).text(), element.child(1).text());
        }
        if (contextMap.isEmpty()) {
            contextResponse.setErrorMessage(properties.getProperty("message.error.context.noResults"));
            return contextResponse;
        }
        contextResponse.setContextResults(contextMap);
        contextResponse.setOK(true);
        return contextResponse;
    }
    public static TranslateResponse getTranslations(Language sourceLanguage, Language targetLanguage, String text) {

        TranslationRequest request = new TranslationRequest(sourceLanguage.getSynonymName(), targetLanguage.getSynonymName(), text);

        TranslateResponse translateResponse = new TranslateResponse(false,null,sourceLanguage.getFullName(),
                targetLanguage.getFullName(), text);

        if(sourceLanguage.equals(targetLanguage)){
            translateResponse.setErrorMessage(properties.getProperty("message.error.translate.sameLanguage"));
            return translateResponse;
        }

        if(sourceLanguage.getTranslateName()==null||targetLanguage.getTranslateName()==null){
            translateResponse.setErrorMessage(properties.getProperty("message.error.translate.unSupportedLanguage"));
            return translateResponse;
        }

        Connection.Response response;
        try {
            response = Jsoup.connect(TRANSLATE_URL)
                    .header("Content-Type", "application/json")
                    .requestBody(request.getAsJson())
                    .method(Connection.Method.POST)
                    .ignoreHttpErrors(true)
                    .ignoreContentType(true)
                    .execute();
        } catch (IOException e) {
            translateResponse.setErrorMessage(properties.getProperty("message.error.connection"));
            return translateResponse;
        }

        String responseBody = response.body();
        translateResponse.setContextTranslations(JSONParser.getContextTranslations(responseBody));
        translateResponse.setTranslatedText(JSONParser.getMainTranslation(responseBody));
        translateResponse.setOK(true);

        return translateResponse;
    }

    public static VoiceResponse getVoiceStream(Voice voice, String text) {

        String base64Text = Base64.getEncoder().encodeToString(text.getBytes());

        VoiceResponse voiceResponse = new VoiceResponse(false,null, voice.getLanguage(), text, voice.getName(), voice.getGender());
        String requestURL = VOICE_URL + "voiceName=" + voice.getName() + "?voiceSpeed=80" + "&" + "inputText=" + base64Text;

        Connection.Response response;
        try {
            response = Jsoup.connect(requestURL)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .execute();
        } catch (IOException e) {
            voiceResponse.setErrorMessage(properties.getProperty("message.error.connection"));
            return voiceResponse;
        }
        if(response.statusCode()==400){
            voiceResponse.setErrorMessage(properties.getProperty("message.error.voiceStream.400"));
            return voiceResponse;
        }
        else if(response.statusCode() == 404) {
            voiceResponse.setErrorMessage(properties.getProperty("message.error.voiceStream.404.textTooLong"));
            return voiceResponse;
        }
        else if (response.bodyAsBytes().length == 9358) {
            voiceResponse.setErrorMessage(properties.getProperty("message.error.voiceStream.9358BytesResponse"));
            return voiceResponse;
        }
        voiceResponse.setOK(true);
        voiceResponse.setMp3Data(response.bodyAsBytes());
        return voiceResponse;
    }

    public static ConjugationResponse getWordConjugation(Language language, String word) {

        ConjugationResponse conjugationResponse = new ConjugationResponse(false,null,language.getFullName(),word);

        if(!language.isConjugate()){
            conjugationResponse.setErrorMessage(properties.getProperty("message.error.conjugation.invalidLanguage"));
            return conjugationResponse;
        }
        String URL = CONJUGATION_URL + "-" + language.getFullName() + "-" + "verb" + "-" + word + ".html";

        Document document = null;

        Connection.Response response = null;
        try {
            response = Jsoup.connect(URL)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .execute();
            document = response.parse();
        } catch (IOException e) {
            conjugationResponse.setErrorMessage(properties.getProperty("message.error.connection"));
            return conjugationResponse;
        }
        if (response.statusCode() == 404) {
            conjugationResponse.setErrorMessage(properties.getProperty("message.error.conjugation.incorrectWord"));
            return conjugationResponse;
        }

        Elements resultBlock = document.getElementsByClass("word-wrap-row");

        Map<String, String[]> conjugationData = new LinkedHashMap<>();

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
        conjugationResponse.setConjugationData(conjugationData);
        conjugationResponse.setOK(true);
        return conjugationResponse;
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

