package reverso.data.response;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import reverso.data.jsonParser.FromStringToArrayDeserializer;

import java.util.List;
import java.util.Map;

public class TranslateResponse extends Response {

    @SerializedName("to")
    private String targetLanguage;

    @SerializedName("translation")
    @JsonAdapter(FromStringToArrayDeserializer.class)
    private String translation;

    @SerializedName("results")
    private Map<String,String> contextTranslations;

    public TranslateResponse(boolean isOK) {
        super(isOK);
    }

    public TranslateResponse(boolean isOK, String sourceLanguage, String targetLanguage, String text, String translation,
                             Map<String,String> contextTranslations) {
        super(isOK, sourceLanguage, text);
        this.targetLanguage = targetLanguage;
        this.translation = translation;
        this.contextTranslations = contextTranslations;
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
