package reverso.data.response;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import reverso.data.jsonParser.FromStringToArrayDeserializer;

public abstract class Response {

    private boolean isOK;
    @SerializedName("from")
    private String sourceLanguage;
    @SerializedName("input")
    @JsonAdapter(FromStringToArrayDeserializer.class)
    private String text;

    public Response(boolean isOK) {
        this.isOK = isOK;
    }

    public Response(boolean isOK, String sourceLanguage, String word) {
        this.isOK = isOK;
        this.sourceLanguage = sourceLanguage;
        this.text = word;
    }

    public boolean isOK() {
        return isOK;
    }

    public void setOK(boolean OK) {
        isOK = OK;
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public String getWord() {
        return text;
    }

    public void setWord(String word) {
        this.text = word;
    }
}
