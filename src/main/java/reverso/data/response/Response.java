package reverso.data.response;

import com.google.gson.GsonBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Response {

    private boolean isOK;

    private String sourceLanguage;

    private String sourceText;

    private String errorMessage;

    public Response(boolean isOK, String errorMessage, String sourceLanguage, String sourceText) {
        this.isOK = isOK;
        this.errorMessage = errorMessage;
        this.sourceLanguage = sourceLanguage;
        this.sourceText = sourceText;
    }

    public Response(boolean isOK, String sourceLanguage, String sourceText) {
        this.isOK = isOK;
        this.sourceLanguage = sourceLanguage;
        this.sourceText = sourceText;
        this.errorMessage=null;
    }

    public String toJson() {
        Map<String, Object> jsonMap = new LinkedHashMap<>();
        jsonMap.put("isOK", isOK());
        jsonMap.put("errorMessage", getErrorMessage());
        jsonMap.put("sourceLanguage", getSourceLanguage());
        jsonMap.put("sourceText", getSourceText());
        addCustomFields(jsonMap);
        return new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(jsonMap);
    }

    protected abstract void addCustomFields(Map<String, Object> jsonMap);

    public boolean isOK() {
        return isOK;
    }

    public void setOK(boolean OK) {
        isOK = OK;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    @Override
    public String toString() {
        return "{\n" +
                "  \"isOK\": " + isOK + ",\n" +
                "  \"sourceLanguage\": \"" + sourceLanguage + "\",\n" +
                "  \"sourceText\": \"" + sourceText + "\",\n" +
                "  \"errorMessage\": \"" + errorMessage + "\"\n" +
                "}";
    }
}
