package reverso;

import com.google.gson.Gson;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import reverso.data.parser.HtmlParser;
import reverso.data.request.RandomUserAgent;
import reverso.data.request.SpellCheckRequest;
import reverso.data.response.impl.*;
import reverso.language.Language;
import reverso.language.Voice;
import java.io.IOException;
import java.util.*;

public class Reverso {
    private HtmlParser parser;
    private Properties properties;
    private static final String SYNONYM_URL = "https://synonyms.reverso.net/synonym/";
    private static final String CONTEXT_URL = "https://context.reverso.net/translation/";
    private static final String VOICE_URL = "https://voice.reverso.net/RestPronunciation.svc/v1/output=json/GetVoiceStream/";
    private static final String CONJUGATION_URL = "https://conjugator.reverso.net/conjugation";
    private static final String SPELLCHECK_URL = "https://orthographe.reverso.net/api/v1/Spelling";

    {
        initializeProperties();
        initializeParser(properties);
    }

    public SynonymResponse getSynonyms(Language language, String word) {
        if (language.getSynonymName() == null) {
            String errorMessage = properties.getProperty("message.error.synonym.unSupportedLanguage");
            return new SynonymResponse(false, errorMessage, language.toString(), word);
        }
        String URL = SYNONYM_URL + language.getSynonymName() + "/" + word;

        Map<String, List<String>> synonymsMap;
        Connection.Response response;
        try {
            response = Jsoup.connect(URL)
                    .header("User-Agent", RandomUserAgent.getRandomUserAgent())
                    .ignoreHttpErrors(true)
                    .execute();
        } catch (IOException e) {
            String errorMessage = properties.getProperty("message.error.connection");
            return new SynonymResponse(false, errorMessage, language.toString(), word);
        }
        if (response.statusCode() == 404) {
            String errorMessage = properties.getProperty("message.error.synonym.noResults");
            return new SynonymResponse(false, errorMessage, language.toString(), word);
        }
        synonymsMap = parser.parseSynonymsPage(response);
        if (synonymsMap.isEmpty()) {
            String message = properties.getProperty("message.error.synonym.noResults");
            return new SynonymResponse(false, message, language.toString(), word);
        }
        return new SynonymResponse(true, language.toString(), word, synonymsMap);
    }
    public ContextResponse getContext(Language sourceLanguage, Language targetLanguage, String word) {

        ContextResponse contextResponse = new ContextResponse(false, null, sourceLanguage.toString(),
                targetLanguage.toString(), word);

        if (sourceLanguage.equals(targetLanguage)) {
            contextResponse.setErrorMessage(properties.getProperty("message.error.context.sameLanguage"));
            return contextResponse;
        }
        String URL = CONTEXT_URL + sourceLanguage + "-" + targetLanguage + "/" + word;

        Document document;
        Map<String, String> contextMap;
        String[] translations;
        Connection.Response response;
        try {
            response = Jsoup.connect(URL)
                    .header("User-Agent", RandomUserAgent.getRandomUserAgent())
                    .ignoreHttpErrors(true)
                    .execute();
            document = response.parse();
        } catch (IOException e) {
            contextResponse.setErrorMessage(properties.getProperty("message.error.connection"));
            return contextResponse;
        }
        if (!document.getElementsByClass("hero-section").isEmpty()) {
            contextResponse.setErrorMessage(properties.getProperty("message.error.context.UnsupportedLanguages"));
            return contextResponse;
        }
        translations = parser.parseContextPageGetTranslations(document);
        if (translations.length == 0) {
            contextResponse.setErrorMessage(properties.getProperty("message.error.context.noResults"));
            return contextResponse;
        }
        contextMap = parser.parseContextPage(document);

        contextResponse.setContextResults(contextMap);
        contextResponse.setOK(true);
        contextResponse.setTranslations(translations);
        return contextResponse;
    }

    public VoiceResponse getVoiceStream(Voice voice, String text) {

        String base64Text = Base64.getEncoder().encodeToString(text.getBytes());

        VoiceResponse voiceResponse = new VoiceResponse(false, null, voice.getLanguage(), text, voice.getName(), voice.getGender());
        String requestURL = VOICE_URL + "voiceName=" + voice.getName() + "?voiceSpeed=80" + "&" + "inputText=" + base64Text;

        Connection.Response response;
        try {
            response = Jsoup.connect(requestURL)
                    .header("User-Agent", RandomUserAgent.getRandomUserAgent())
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .execute();
        } catch (IOException e) {
            voiceResponse.setErrorMessage(properties.getProperty("message.error.connection"));
            return voiceResponse;
        }
        if (response.statusCode() == 400) {
            voiceResponse.setErrorMessage(properties.getProperty("message.error.voiceStream.400"));
            return voiceResponse;
        } else if (response.statusCode() == 404) {
            voiceResponse.setErrorMessage(properties.getProperty("message.error.voiceStream.404.textTooLong"));
            return voiceResponse;
        } else if (response.bodyAsBytes().length == 9358) {
            voiceResponse.setErrorMessage(properties.getProperty("message.error.voiceStream.9358BytesResponse"));
            return voiceResponse;
        }
        voiceResponse.setOK(true);
        voiceResponse.setMp3Data(response.bodyAsBytes());
        return voiceResponse;
    }

    public ConjugationResponse getWordConjugation(Language language, String word) {

        ConjugationResponse conjugationResponse = new ConjugationResponse(false, null, language.toString(), word);

        if (!language.isConjugate()) {
            conjugationResponse.setErrorMessage(properties.getProperty("message.error.conjugation.invalidLanguage"));
            return conjugationResponse;
        }
        String URL = CONJUGATION_URL + "-" + language.toString() + "-" + "verb" + "-" + word + ".html";

        Map<String, String[]> conjugationData;
        Connection.Response response;
        try {
            response = Jsoup.connect(URL)
                    .header("User-Agent", RandomUserAgent.getRandomUserAgent())
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .execute();
        } catch (IOException e) {
            conjugationResponse.setErrorMessage(properties.getProperty("message.error.connection"));
            return conjugationResponse;
        }
        if (response.statusCode() == 404) {
            conjugationResponse.setErrorMessage(properties.getProperty("message.error.conjugation.incorrectWord"));
            return conjugationResponse;
        }
        conjugationData = parser.parseConjugationPage(response);

        conjugationResponse.setConjugationData(conjugationData);
        conjugationResponse.setOK(true);
        return conjugationResponse;
    }
    public SpellCheckResponse getSpellCheck(Language language, String text) {

        if (language.getSpellCheckName() == null) {
            return new SpellCheckResponse(false, properties.getProperty("message.error.spellCheck.unsupportedLanguage"),
                    language.toString(), text);
        }

        String requestJson = SpellCheckRequest.builder().withLanguage(language.getSpellCheckName())
                .withText(text)
                .withCorrectionDetails(false)
                .toJson();

        Connection.Response response;
        String responseData;
        try {
            response = Jsoup.connect(SPELLCHECK_URL)
                    .header("Content-Type", "application/json")
                    .header("User-Agent", RandomUserAgent.getRandomUserAgent())
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .requestBody(requestJson)
                    .method(Connection.Method.POST)
                    .execute();
            responseData = response.body();
        } catch (IOException e) {
            return new SpellCheckResponse(false, properties.getProperty("message.error.connection"), language.toString(), text);
        }
        SpellCheckResponse spellCheckResponse = new Gson().fromJson(responseData, SpellCheckResponse.class);

        if (spellCheckResponse.getCorrectedText().equals(text)) {
            return new SpellCheckResponse(false, properties.getProperty("message.error.spellCheck.noErrorsOrMismatchedLanguage"),
                    language.toString(), text);
        }
            spellCheckResponse.setOK(true);
            spellCheckResponse.setSourceText(text);
            spellCheckResponse.setSourceLanguage(language.toString());

            return spellCheckResponse;
    }

    private void initializeProperties() {
        try {
            properties = new Properties();
            properties.load(Reverso.class.getResourceAsStream("/messages.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Problem during properties file initialization. Possibly the path is incorrect");
        }
    }

    private void initializeParser(Properties properties){
        parser = new HtmlParser(properties);
    }
}


