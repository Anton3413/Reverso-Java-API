package reverso;


import reverso.supportedLanguages.Language;

public class Main {
    public static void main(String[] args) {
        Reverso.getTranslations(Language.ENGLISH,Language.RUSSIAN,"hello");
    }
}