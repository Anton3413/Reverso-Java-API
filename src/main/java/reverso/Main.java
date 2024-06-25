package reverso;


import reverso.data.response.Response;
import reverso.data.response.impl.*;
import reverso.supportedLanguages.Language;
import reverso.supportedLanguages.Voice;

import java.util.Arrays;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
    /*ContextResponse response = Reverso.getContext(Language.ENGLISH,Language.RUSSIAN,"голова");

        ConjugationResponse conjugationResponse = Reverso.getWordConjugation(Language.RUSSIAN,"идти");

        if(!conjugationResponse.isOK()) {
            System.out.println(conjugationResponse.getErrorMessage());
        }
        else {
            for (Map.Entry<String, String[]> entry : conjugationResponse.getConjugationData().entrySet()) {
                System.out.println("Source: " + entry.getKey() + " -> Target: " + Arrays.toString(entry.getValue()));
            }
        }*/


        SynonymResponse response = Reverso.getSynonyms(Language.SWEDISH,"Skön");

        System.out.println(response.toJson());

    }
}