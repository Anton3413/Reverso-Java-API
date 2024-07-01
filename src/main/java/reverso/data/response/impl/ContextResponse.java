package reverso.data.response.impl;

import reverso.data.response.Response;

import java.util.Map;

public class ContextResponse extends Response {

    private String targetLanguage;
    private Map<String,String> contextResults;
    private String[] translations;

    public ContextResponse(boolean isOK, String errorMessage, String sourceLanguage,
                           String targetLanguage, String text) {
        super(isOK,errorMessage,sourceLanguage, text );
        this.targetLanguage = targetLanguage;
        this.contextResults = null;
    }

    public ContextResponse(boolean isOK, String sourceLanguage,
                           String targetLanguage, String text,
                           Map<String,String> contextResults,
                           String[] translations ) {
        super(isOK, sourceLanguage, text);
        this.targetLanguage = targetLanguage;
        this.contextResults = contextResults;
        this.translations = translations;
    }
    @Override
    protected void addCustomFields(Map<String, Object> jsonMap) {
        jsonMap.put("targetLanguage", targetLanguage);
        jsonMap.put("contextResults", contextResults);
        jsonMap.put("translations", translations);
    }

    public Map<String, String> getContextResults() {
        return contextResults;
    }

    public void setContextResults(Map<String, String> contextResults) {
        this.contextResults = contextResults;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public String[] getTranslations() {
        return translations;
    }

    public void setTranslations(String[] translations) {
        this.translations = translations;
    }
}
