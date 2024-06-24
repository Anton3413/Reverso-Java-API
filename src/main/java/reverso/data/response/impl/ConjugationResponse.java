package reverso.data.response.impl;

import reverso.data.response.Response;

import java.util.HashMap;
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
}