package reverso.data.response.impl;

import com.google.gson.Gson;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import reverso.data.jsonParser.FromStringToArrayDeserializer;
import reverso.data.response.Response;

import java.util.Map;

public class TranslateResponse extends Response {

    @SerializedName("to")
    private String targetLanguage;

    @SerializedName("translation")
    @JsonAdapter(FromStringToArrayDeserializer.class)
    private String translation;

    @SerializedName("results")
    private Map<String,String> contextTranslations;

    public TranslateResponse(boolean isOK,String errorMessage, String sourceLanguage, String targetLanguage, String text) {
        super(isOK,errorMessage,sourceLanguage,text);
        this.targetLanguage = targetLanguage;
    }

    public TranslateResponse(boolean isOK, String sourceLanguage, String targetLanguage, String text, String translation,
                             Map<String,String> contextTranslations) {
        super(isOK, sourceLanguage, text);
        this.targetLanguage = targetLanguage;
        this.translation = translation;
        this.contextTranslations = contextTranslations;
    }

    @Override
    protected void addCustomFields(Map<String, Object> jsonMap) {
        jsonMap.put("targetLanguage", targetLanguage);
        jsonMap.put("translation", translation);
        jsonMap.put("contextTranslations", contextTranslations);
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public String getTranslatedText() {
        return translation;
    }

    public void setTranslatedText(String translation) {
        this.translation = translation;
    }

    public Map<String,String>  getContextTranslations() {
        return contextTranslations;
    }

    public void setContextTranslations(Map<String,String> contextTranslations) {
        this.contextTranslations = contextTranslations;
    }
}
