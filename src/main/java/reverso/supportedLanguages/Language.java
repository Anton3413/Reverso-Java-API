package reverso.supportedLanguages;

public enum Language {
    ARABIC("arabic", "ar","ara"),
    GERMAN("german", "de","ger"),
    ENGLISH("english", "en","eng"),
    SPANISH("spanish", "es","spa"),
    FRENCH("french", "fr","fra"),
    HEBREW("hebrew", "he","heb"),
    ITALIAN("italian", "it","ita"),
    JAPANESE("japanese", "ja","jpn"),
    KOREAN("korean", null,"kor"),
    DUTCH("dutch", "nl","dut"),
    POLISH("polish", "pl","pol"),
    PORTUGUESE("portuguese", "pt","por"),
    ROMANIAN("romanian", "ro","rum"),
    RUSSIAN("russian", "ru","rus"),
    SWEDISH("swedish", null,"swe"),
    TURKISH("turkish", null,"tur");


    Language(String fullName, String synonymName,String translateName){
        this.fullName = fullName;
        this.synonymName = synonymName;
        this.translateName = translateName;
    }

    private final String fullName;
    private final String synonymName;
    private final String translateName;

    public String getSynonymName() {
        return this.synonymName;
    }
    public String getFullName() {
        return this.fullName;
    }

    public String getTranslateName() {
        return this.translateName;
    }
}
