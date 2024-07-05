package reverso.language;

public enum Language {
    ARABIC("ar", true, null),
    GERMAN("de", true, null),
    ENGLISH("en", true, "eng"),
    SPANISH("es", true, "spa"),
    FRENCH("fr", true, "fra"),
    HEBREW("he", true, null),
    ITALIAN("it", true, "ita"),
    JAPANESE("ja", true, null),
    KOREAN(null, false, null),
    DUTCH("nl", false, null),
    POLISH("pl", false, null),
    PORTUGUESE("pt", true, null),
    ROMANIAN("ro", false, null),
    RUSSIAN("ru", true, null),
    SWEDISH(null, false, null),
    TURKISH(null, false, null),
    UKRAINIAN(null, false, null);

    private final String synonymName;
    private final boolean isConjugate;
    private final String spellCheckName;

    Language(String synonymName, boolean isConjugate, String spellCheckName) {
        this.synonymName = synonymName;
        this.isConjugate = isConjugate;
        this.spellCheckName = spellCheckName;
    }

    public String toString(){
        return this.name().toLowerCase();
    }

    public String getSynonymName() {
        return this.synonymName;
    }

    public boolean isConjugate() {
        return isConjugate;
    }

    public String getSpellCheckName() {
        return spellCheckName;
    }
}
