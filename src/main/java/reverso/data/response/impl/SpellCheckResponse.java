package reverso.data.response.impl;

import com.google.gson.annotations.SerializedName;
import reverso.data.response.Response;
import reverso.data.response.entity.Stats;

import java.util.Map;

public class SpellCheckResponse extends Response {


    @SerializedName("text")
    private String correctedText;

    private String unspelledText;

    private boolean truncated;

    private Stats stats;

    public SpellCheckResponse(boolean isOk, String errorMessage, String sourceLanguage, String sourceText) {
        super(isOk,errorMessage,sourceLanguage,sourceText);
    }

    @Override
    protected void addCustomFields(Map<String, Object> jsonMap) {
        jsonMap.put("correctedText", correctedText);
        jsonMap.put("unspelledText", unspelledText);
        jsonMap.put("truncated", truncated);
        jsonMap.put("stats", stats);
    }

    public String getCorrectedTextAsString() {
        return correctedText;
    }

    public void setCorrectedText(String correctedText) {
        this.correctedText = correctedText;
    }

    public String getUnspelledText() {
        return unspelledText;
    }

    public void setUnspelledText(String unspelledText) {
        this.unspelledText = unspelledText;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

}
