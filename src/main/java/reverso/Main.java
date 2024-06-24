package reverso;


import reverso.data.response.impl.ContextResponse;
import reverso.data.response.impl.TranslateResponse;
import reverso.supportedLanguages.Language;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
    ContextResponse response = Reverso.getContext(Language.ENGLISH,Language.RUSSIAN,"голова");

       /* System.out.println(response.getResults());*/

        for (Map.Entry<String, String> entry : response.getContextResults().entrySet()) {
            System.out.println("Source: " + entry.getKey() + " -> Target: " + entry.getValue());
        }


    }
}