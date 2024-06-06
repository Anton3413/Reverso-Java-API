package reverso.data.response;

import java.util.Map;

public class ContextResponse extends Response {

    private String targetLanguage;
    private Map<String,String> results;

    public ContextResponse(boolean isOK) {
        super(isOK);
    }

    public ContextResponse(boolean isOK, String sourceLanguage, String word, Map<String, String> results) {
        super(isOK, sourceLanguage, word);
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
