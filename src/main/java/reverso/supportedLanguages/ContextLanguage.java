package reverso.supportedLanguages;

public enum ContextLanguage {
    ARABIC("arabic"),
    GERMAN("german"),
    ENGLISH("english"),
    SPANISH("spanish"),
    FRENCH("french"),
    HEBREW("hebrew"),
    ITALIAN("italian"),
    JAPANESE("japanese"),
    KOREAN("korean"),
    DUTCH("dutch"),
    POLISH("polish"),
    PORTUGUESE("portuguese"),
    ROMANIAN("romanian"),
    RUSSIAN("russian"),
    SWEDISH("swedish"),
    TURKISH("turkish"),
    UKRAINIAN("ukrainian"),
    CHINESE("chinese");

    private final String name;


    ContextLanguage(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
