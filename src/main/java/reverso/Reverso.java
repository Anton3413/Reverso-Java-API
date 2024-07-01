package reverso;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import reverso.data.parser.HtmlParser;
import reverso.data.response.impl.*;
import reverso.supportedLanguages.Language;
import reverso.supportedLanguages.Voice;
import java.io.IOException;
import java.util.*;

public class Reverso {
    private HtmlParser parser;
    private Properties properties;
    private static final String SYNONYM_URL = "https://synonyms.reverso.net/synonym/";
    private static final String CONTEXT_URL = "https://context.reverso.net/translation/";
    private static final String VOICE_URL = "https://voice.reverso.net/RestPronunciation.svc/v1/output=json/GetVoiceStream/";
    private static final String CONJUGATION_URL = "https://conjugator.reverso.net/conjugation";

    {
        initializeProperties();
        initializeParser(properties);
    }

    public SynonymResponse getSynonyms(Language language, String word) {
        if (language.getSynonymName() == null) {
            String errorMessage = properties.getProperty("message.error.synonym.unSupportedLanguage");
            return new SynonymResponse(false, errorMessage, language.getFullName(), word);
        }
        String URL = SYNONYM_URL + language.getSynonymName() + "/" + word;

        Map<String, List<String>> synonymsMap;
        Connection.Response response;
        try {
            response = Jsoup.connect(URL)
                    .ignoreHttpErrors(true)
                    .execute();
        } catch (IOException e) {
            String errorMessage = properties.getProperty("message.error.connection");
            return new SynonymResponse(false, errorMessage, language.getFullName(), word);
        }
        if (response.statusCode() == 404) {
            String errorMessage = properties.getProperty("message.error.synonym.noResults");
            return new SynonymResponse(false, errorMessage, language.getFullName(), word);
        }
        synonymsMap = parser.parseSynonymsPage(response);
        if (synonymsMap.isEmpty()) {
            String message = properties.getProperty("message.error.synonym.noResults");
            return new SynonymResponse(false, message, language.getFullName(), word);
        }
        return new SynonymResponse(true, language.getFullName(), word, synonymsMap);
    }

    public ContextResponse getContext(Language sourceLanguage, Language targetLanguage, String word) {

        ContextResponse contextResponse = new ContextResponse(false, null, sourceLanguage.getFullName(),
                targetLanguage.getFullName(), word);

        if (sourceLanguage.equals(targetLanguage)) {
            contextResponse.setErrorMessage(properties.getProperty("message.error.context.sameLanguage"));
            return contextResponse;
        }
        String URL = CONTEXT_URL + sourceLanguage.getFullName() + "-" + targetLanguage.getFullName() + "/" + word;

        Document document;
        Map<String, String> contextMap;
        String[] translations;
        Connection.Response response;
        try {
            response = Jsoup.connect(URL)
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

        ConjugationResponse conjugationResponse = new ConjugationResponse(false, null, language.getFullName(), word);

        if (!language.isConjugate()) {
            conjugationResponse.setErrorMessage(properties.getProperty("message.error.conjugation.invalidLanguage"));
            return conjugationResponse;
        }
        String URL = CONJUGATION_URL + "-" + language.getFullName() + "-" + "verb" + "-" + word + ".html";

        Map<String, String[]> conjugationData;
        Connection.Response response;
        try {
            response = Jsoup.connect(URL)
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


