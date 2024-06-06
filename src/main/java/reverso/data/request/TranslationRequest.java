package reverso.data.request;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;

public class TranslationRequest {
    @SerializedName("from")
    private final String sourceLanguage;
    @SerializedName("to")
    private final String targetLanguage;
    @SerializedName("input")
    private final String text;
    private final String format;
    private final Map<String, String> options;
    {
        format = "text";
        options = new HashMap<>();
        options.put("contextResults","true");
        options.put("languageDetection","true");
        options.put("origin","reverso");
    }
    public TranslationRequest(String sourceLanguage, String targetLanguage, String text) {
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
        this.text = text;
    }

    @Override
    public String toString() {
        return "TranslationRequest{" +
                "sourceLanguage='" + sourceLanguage + '\'' +
                ", targetLanguage='" + targetLanguage + '\'' +
                ", text='" + text + '\'' +
                ", format='" + format + '\'' +
                ", options=" + options +
                '}';
    }
}
