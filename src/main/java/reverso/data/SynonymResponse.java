package reverso.data;

import java.util.List;
import java.util.Map;

public class SynonymResponse extends Response {

    private Map<String,List<String>> synonyms;

    public SynonymResponse(boolean isOK) {
        super(isOK);
    }

    public SynonymResponse(boolean isOK, String sourceLanguage, String word, Map<String, List<String>> synonyms) {
        super(isOK, sourceLanguage, word);
        this.synonyms = synonyms;
    }
    
    public Map<String, List<String>> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(Map<String, List<String>> synonyms) {
        this.synonyms = synonyms;
    }
}
