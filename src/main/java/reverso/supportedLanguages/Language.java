package reverso.supportedLanguages;

public enum Language {
    ARABIC("arabic", "ar"),
    GERMAN("german", "de"),
    ENGLISH("english", "en"),
    SPANISH("spanish", "es"),
    FRENCH("french", "fr"),
    HEBREW("hebrew", "he"),
    ITALIAN("italian", "it"),
    JAPANESE("japanese", "ja"),
    KOREAN("korean", null),
    DUTCH("dutch", "nl"),
    POLISH("polish", "pl"),
    PORTUGUESE("portuguese", "pt"),
    ROMANIAN("romanian", "ro"),
    RUSSIAN("russian", "ru"),
    SWEDISH("swedish", null),
    TURKISH("turkish", null),
    UKRAINIAN("ukrainian", null),
    CHINESE("chinese", null);

    Language(String fullName, String shortName){
        this.fullName = fullName;
        this.shortName = shortName;
    }

    private final String fullName;
    private final String shortName;

    public String getShortName() {
        return this.shortName;
    }
    public String getFullName() {
        return this.fullName;
    }
}
