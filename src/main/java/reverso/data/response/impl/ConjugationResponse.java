package reverso.data.response.impl;

import reverso.data.response.Response;

import java.util.Map;

public class ConjugationResponse extends Response {

    private Map<String,String[]> conjugationData;

    public ConjugationResponse(boolean isOK,String errorMessage,String sourceLanguage,String text) {
        super(isOK,errorMessage,sourceLanguage,text);
        this.conjugationData = null;
    }

    public ConjugationResponse(boolean isOK, String sourceLanguage, String text, Map<String,String[]> data) {
        super(isOK, sourceLanguage, text);
        this.conjugationData = data;
    }
    @Override
    protected void addCustomFields(Map<String, Object> jsonMap) {
        jsonMap.put("conjugationData", conjugationData);
    }

    public Map<String, String[]> getConjugationData() {
        return conjugationData;
    }
    public void setConjugationData(Map<String, String[]> conjugationData) {
        this.conjugationData = conjugationData;
    }

    public String getConjugationAsString(){
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, String[]> entry : conjugationData.entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();

            builder.append(key).append(":\n");
            for (String value : values) {
                builder.append("  - ").append(value).append("\n");
            }
        }
        return builder.toString();
    }
}