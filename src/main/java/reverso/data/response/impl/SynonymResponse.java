package reverso.data.response.impl;

import reverso.data.response.Response;
import java.util.List;
import java.util.Map;

public class SynonymResponse extends Response {

    private Map<String,List<String>> synonyms;

    public SynonymResponse(boolean isOK, String errorMessage, String language, String word) {
        super(isOK,errorMessage,language,word);
    }

    public SynonymResponse(boolean isOK, String sourceLanguage, String text, Map<String, List<String>> synonyms) {
        super(isOK, sourceLanguage, text);
        this.synonyms = synonyms;
    }
    @Override
    protected void addCustomFields(Map<String, Object> jsonMap) {
        jsonMap.put("synonyms", synonyms);
    }

    public Map<String, List<String>> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(Map<String, List<String>> synonyms) {
        this.synonyms = synonyms;
    }

    public String getSynonymsAsString() {
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, List<String>> entry : synonyms.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();

            builder.append("**").append(key).append("**:\n");

            for (String value : values) {
                builder.append("- ").append(value).append("\n");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
