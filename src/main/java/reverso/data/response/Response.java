package reverso.data.response;


import com.google.gson.GsonBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Response {

    private boolean isOK;

    private String sourceLanguage;

    private String text;

    private String errorMessage;

    public Response(boolean isOK, String errorMessage, String sourceLanguage, String text) {
        this.isOK = isOK;
        this.errorMessage = errorMessage;
        this.sourceLanguage = sourceLanguage;
        this.text = text;
    }

    public Response(boolean isOK, String sourceLanguage, String text) {
        this.isOK = isOK;
        this.sourceLanguage = sourceLanguage;
        this.text = text;
        this.errorMessage=null;
    }
    public String toJson() {
        Map<String, Object> jsonMap = new LinkedHashMap<>();
        jsonMap.put("isOK", isOK());
        jsonMap.put("errorMessage", getErrorMessage());
        jsonMap.put("sourceLanguage", getSourceLanguage());
        jsonMap.put("text", getText());
        addCustomFields(jsonMap);
        return new GsonBuilder().setPrettyPrinting().create().toJson(jsonMap);
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
                "  \"text\": \"" + text + "\",\n" +
                "  \"errorMessage\": \"" + errorMessage + "\"\n" +
                "}";
    }
}
