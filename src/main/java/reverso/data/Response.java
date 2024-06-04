package reverso.data;

import java.util.List;
import java.util.Map;

public abstract class Response {

    private boolean isOK;
    private String sourceLanguage;
    private String word;


    public Response(boolean isOK) {
        this.isOK = isOK;
    }

    public Response(boolean isOK, String sourceLanguage, String word) {
        this.isOK = isOK;
        this.sourceLanguage = sourceLanguage;
        this.word = word;
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
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
