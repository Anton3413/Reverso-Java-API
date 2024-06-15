package reverso.data.response.impl;

import reverso.data.response.Response;

import java.util.Map;

public class ContextResponse extends Response {

    private String targetLanguage;
    private Map<String,String> results;

    public ContextResponse(boolean isOK, String errorMessage, String sourceLanguage,
                           String targetLanguage, String text) {
        super(isOK,errorMessage,sourceLanguage, text );
        this.targetLanguage = targetLanguage;
        this.results = null;
    }

    public ContextResponse(boolean isOK, String sourceLanguage,
                           String targetLanguage, String text,
                           Map<String,String> results) {
        super(isOK, sourceLanguage, text);
        this.targetLanguage = targetLanguage;
        this.results = results;
    }

    public Map<String, String> getResults() {
        return results;
    }

    public void setResults(Map<String, String> results) {
        this.results = results;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }
}
