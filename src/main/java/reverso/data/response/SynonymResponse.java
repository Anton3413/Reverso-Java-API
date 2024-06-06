package reverso.data.response;

import java.util.List;
import java.util.Map;

public class SynonymResponse extends Response {

    private Map<String,List<String>> synonyms;

    public SynonymResponse(boolean isOK) {
        super(isOK);
    }

    public SynonymResponse(boolean isOK, String sourceLanguage, String text, Map<String, List<String>> synonyms) {
        super(isOK, sourceLanguage, text);
        this.synonyms = synonyms;
    }
    
    public Map<String, List<String>> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(Map<String, List<String>> synonyms) {
        this.synonyms = synonyms;
    }
}
