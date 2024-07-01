package reverso;

import reverso.data.response.impl.*;
import reverso.supportedLanguages.Language;

import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        Reverso reverso = new Reverso();

        ContextResponse response = reverso.getContext(Language.POLISH,Language.PORTUGUESE,"какой");

        System.out.println(response.toJson());
        System.out.println(Arrays.toString(response.getTranslations()));
    }
}