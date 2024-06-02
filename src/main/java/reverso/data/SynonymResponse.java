package reverso.data;

import java.util.List;
import java.util.Map;

public class SynonymResponse {

    private boolean isOK;
    private String sourceLanguage;
    private String word;
    private Map<String,List<String>> synonyms;

    public SynonymResponse(boolean isOK) {
        this.isOK = isOK;
    }

    public SynonymResponse(boolean isOK, String sourceLanguage, String word, Map<String, List<String>> synonyms) {
        this.isOK = isOK;
        this.sourceLanguage = sourceLanguage;
        this.word = word;
        this.synonyms = synonyms;
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

    public Map<String, List<String>> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(Map<String, List<String>> synonyms) {
        this.synonyms = synonyms;
    }
}
