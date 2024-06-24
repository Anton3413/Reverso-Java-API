package reverso;


import reverso.data.response.impl.TranslateResponse;
import reverso.supportedLanguages.Language;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
    TranslateResponse response = Reverso.getTranslations(Language.ENGLISH,Language.RUSSIAN,"hello");

        System.out.println(response.getContextTranslations());

        for (Map.Entry<String, String> entry : response.getContextTranslations().entrySet()) {
            System.out.println("Source: " + entry.getKey() + " -> Target: " + entry.getValue());
        }
    }
}