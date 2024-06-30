package reverso.supportedLanguages;

public enum Language {
    ARABIC("arabic", "ar","ara",true),
    GERMAN("german", "de","ger",true),
    ENGLISH("english", "en","eng",true),
    SPANISH("spanish", "es","spa",true),
    FRENCH("french", "fr","fra",true),
    HEBREW("hebrew", "he","heb",true),
    ITALIAN("italian", "it","ita",true),
    JAPANESE("japanese", "ja","jpn",true),
    KOREAN("korean", null,"kor",false),
    DUTCH("dutch", "nl","dut",false),
    POLISH("polish", "pl","pol",false),
    PORTUGUESE("portuguese", "pt","por",true),
    ROMANIAN("romanian", "ro","rum",false),
    RUSSIAN("russian", "ru","rus",true),
    SWEDISH("swedish", null,"swe",false),
    TURKISH("turkish", null,"tur",false),
    UKRAINIAN("ukrainian",null,null,false);

    Language(String fullName, String synonymName,String translateName, boolean conjugate){
        this.fullName = fullName;
        this.synonymName = synonymName;
        this.translateName = translateName;
        this.conjugate = conjugate;
    }

    private final String fullName;
    private final String synonymName;
    private final String translateName;
    private final boolean conjugate;

    public String getSynonymName() {
        return this.synonymName;
    }
    public String getFullName() {
        return this.fullName;
    }
    public String getTranslateName() {
        return this.translateName;
    }

    public boolean isConjugate() {
        return conjugate;
    }
}
