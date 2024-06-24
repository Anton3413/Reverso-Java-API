package reverso;


import reverso.data.response.Response;
import reverso.data.response.impl.ConjugationResponse;
import reverso.data.response.impl.ContextResponse;
import reverso.data.response.impl.SynonymResponse;
import reverso.data.response.impl.TranslateResponse;
import reverso.supportedLanguages.Language;

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

        Response synonymResponse = Reverso.getSynonyms(Language.ENGLISH,"world");

       // System.out.println(synonymResponse);
        System.out.println(synonymResponse);

    }
}