package reverso.supportedLanguages;

public enum SynonymLanguage {

    ARABIC("ar"),
    GERMAN("de"),
    ENGLISH("en"),
    SPANISH("es"),
    FRENCH("fr"),
    HEBREW("he"),
    ITALIAN("it"),
    JAPANESE("ja"),
    DUTCH("nl"),
    POLISH("pl"),
    PORTUGUESE("pt"),
    ROMANIAN("ro"),
    RUSSIAN("ru");

    SynonymLanguage(String shortName){
        this.shortName = shortName;
    }

    private final String shortName;

    @Override
    public String toString() {
        return this.shortName;
    }
}
