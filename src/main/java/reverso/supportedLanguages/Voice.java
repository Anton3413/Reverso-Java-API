package reverso.supportedLanguages;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public enum Voice {
    ARABIC_LEILA("Leila22k", "Arabic", "F"),
    ARABIC_MEHDI("Mehdi22k", "Arabic", "M"),
    ARABIC_NIZAR("Nizar22k", "Arabic", "M"),
    ARABIC_SALMA("Salma22k", "Arabic", "F"),
    ENGLISH_LISA("Lisa22k", "Australian English", "F"),
    ENGLISH_TYLER("Tyler22k", "Australian English", "M"),
    BELGIAN_DUTCH_JEROEN("Jeroen22k", "Belgian Dutch", "M"),
    BELGIAN_DUTCH_SOFIE("Sofie22k", "Belgian Dutch", "F"),
    BELGIAN_DUTCH_ZOE("Zoe22k", "Belgian Dutch", "F"),
    BELGIAN_FRENCH_ALICE("Alice-BE22k", "Belgian French", "F"),
    BELGIAN_FRENCH_ANAIS("Anais-BE22k", "Belgian French", "F"),
    BELGIAN_FRENCH_ANTOINE("Antoine-BE22k", "Belgian French", "M"),
    BELGIAN_FRENCH_BRUNO("Bruno-BE22k", "Belgian French", "M"),
    BELGIAN_FRENCH_CLAIRE("Claire-BE22k", "Belgian French", "F"),
    BELGIAN_FRENCH_JULIE("Julie-BE22k", "Belgian French", "F"),
    BELGIAN_FRENCH_JUSTINE("Justine22k", "Belgian French", "F"),
    BELGIAN_FRENCH_MANON("Manon-BE22k", "Belgian French", "F"),
    BELGIAN_FRENCH_MARGAUX("Margaux-BE22k", "Belgian French", "F"),
    BRAZILIAN_MARCIA("Marcia22k", "Brazilian", "F"),
    BRITISH_GRAHAM("Graham22k", "British", "M"),
    BRITISH_LUCY("Lucy22k", "British", "F"),
    BRITISH_PETER("Peter22k", "British", "M"),
    BRITISH_QUEEN_ELIZABETH("QueenElizabeth22k", "British", "F"),
    BRITISH_RACHEL("Rachel22k", "British", "F"),
    CANADIAN_FRENCH_LOUISE("Louise22k", "Canadian French", "F"),
    CATALAN_LAIA("Laia22k", "Catalan", "F"),
    CZECH_ELISKA("Eliska22k", "Czech", "F"),
    DANISH_METTE("Mette22k", "Danish", "F"),
    DANISH_RASMUS("Rasmus22k", "Danish", "M"),
    DUTCH_DAAN("Daan22k", "Dutch", "M"),
    DUTCH_FEMKE("Femke22k", "Dutch", "F"),
    DUTCH_JASMIJN("Jasmijn22k", "Dutch", "F"),
    DUTCH_MAX("Max22k", "Dutch", "M"),
    FINLAND_SWEDISH_SAMUEL("Samuel22k", "Finland Swedish", "M"),
    FINNISH_SANNA("Sanna22k", "Finnish", "F"),
    FRENCH_ANAIS("Anais22k", "French", "F"),
    FRENCH_ANTOINE("Antoine22k", "French", "M"),
    FRENCH_BRUNO("Bruno22k", "French", "M"),
    FRENCH_CLAIRE("Claire22k", "French", "F"),
    FRENCH_JULIE("Julie22k", "French", "F"),
    FRENCH_MANON("Manon22k", "French", "F"),
    FRENCH_MARGAUX("Margaux22k", "French", "F"),
    GERMAN_ANDREAS("Andreas22k", "German", "M"),
    GERMAN_CLAUDIA("Claudia22k", "German", "F"),
    GERMAN_JULIA("Julia22k", "German", "F"),
    GERMAN_KLAUS("Klaus22k", "German", "M"),
    GERMAN_SARAH("Sarah22k", "German", "F"),
    GOTHENBURG_SWEDISH_KAL("Kal22k", "Gothenburg Swedish", "M"),
    GREEK_DIMITRIS("Dimitris22k", "Greek", "M"),
    HEBREW_AVRINEURAL("he-IL-AvriNeural", "Hebrew", "M"),
    INDIAN_ENGLISH_DEEPA("Deepa22k", "Indian English", "F"),
    ITALIAN_CHIARA("Chiara22k", "Italian", "F"),
    ITALIAN_FABIANA("Fabiana22k", "Italian", "F"),
    ITALIAN_VITTORIO("Vittorio22k", "Italian", "M"),
    JAPANESE_SAKURA("Sakura22k", "Japanese", "F"),
    KOREAN_MINJI("Minji22k", "Korean", "F"),
    MANDARIN_CHINESE_LULU("Lulu22k", "Mandarin Chinese", "F"),
    NORWEGIAN_BENTE("Bente22k", "Norwegian", "F"),
    NORWEGIAN_KARI("Kari22k", "Norwegian", "F"),
    NORWEGIAN_OLAV("Olav22k", "Norwegian", "M"),
    POLISH_ANIA("Ania22k", "Polish", "F"),
    POLISH_MONIKA("Monika22k", "Polish", "F"),
    PORTUGUESE_CELIA("Celia22k", "Portuguese", "F"),
    ROMANIAN_EMILNEURAL("ro-RO-EmilNeural", "Romanian", "M"),
    RUSSIAN_ALYONA("Alyona22k", "Russian", "F"),
    SCANIAN_MIA("Mia22k", "Scanian", "F"),
    SPANISH_ANTONIO("Antonio22k", "Spanish", "M"),
    SPANISH_INES("Ines22k", "Spanish", "F"),
    SPANISH_MARIA("Maria22k", "Spanish", "F"),
    SWEDISH_ELIN("Elin22k", "Swedish", "F"),
    SWEDISH_EMIL("Emil22k", "Swedish", "M"),
    SWEDISH_EMMA("Emma22k", "Swedish", "F"),
    SWEDISH_ERIK("Erik22k", "Swedish", "M"),
    TURKISH_IPEK("Ipek22k", "Turkish", "F"),
    US_ENGLISH_HEATHER("Heather22k", "US English", "F"),
    US_ENGLISH_KAREN("Karen22k", "US English", "F"),
    US_ENGLISH_KENNY("Kenny22k", "US English", "M"),
    US_ENGLISH_LAURA("Laura22k", "US English", "F"),
    US_ENGLISH_MICAH("Micah22k", "US English", "M"),
    US_ENGLISH_NELLY("Nelly22k", "US English", "F"),
    US_ENGLISH_ROD("Rod22k", "US English", "M"),
    US_ENGLISH_RYAN("Ryan22k", "US English", "M"),
    US_ENGLISH_SAUL("Saul22k", "US English", "M"),
    US_ENGLISH_SHARON("Sharon22k", "US English", "F"),
    US_ENGLISH_TRACY("Tracy22k", "US English", "F"),
    US_ENGLISH_WILL("Will22k", "US English", "M"),
    US_SPANISH_RODRIGO("Rodrigo22k", "US Spanish", "M"),
    US_SPANISH_ROSA("Rosa22k", "US Spanish", "F");

    private final String name;
    private final String language;
    private final String gender;

    Voice(String name, String language, String gender) {
        this.name = name;
        this.language = language;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public String getGender() {
        return gender;
    }

    public static Voice getRandomEnglish(){
        Voice[] voices = Arrays.stream(Voice.values())
                .filter(voice -> voice.getLanguage().contains("English") || voice.getLanguage().contains("British")).toArray(Voice[]::new);

        System.out.println(voices.length);
        int randomIndex = ThreadLocalRandom.current().nextInt(voices.length);
        return voices[randomIndex];
    }
}