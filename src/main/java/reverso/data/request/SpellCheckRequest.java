package reverso.data.request;

import com.google.gson.GsonBuilder;

public class SpellCheckRequest {
    private String language;
    private boolean correctionDetails;
    private String origin;
    private String text;

    {
        this.origin = "interactive";
    }

    private SpellCheckRequest() {
    }


    public String getLanguage() {
        return language;
    }

    public String toJson() {
        return new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(this);
    }

    public static SpellCheckRequest builder() {
        return new SpellCheckRequest();
    }

    public SpellCheckRequest withLanguage(String language) {
        this.language = language;
        return this;
    }

    public SpellCheckRequest withCorrectionDetails(boolean correctionDetails) {
        this.correctionDetails= correctionDetails;
        return this;
    }

    public SpellCheckRequest withOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    public SpellCheckRequest withText(String text) {
        this.text = text;
        return this;
    }
}