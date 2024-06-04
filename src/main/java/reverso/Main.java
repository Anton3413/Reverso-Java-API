package reverso;

import reverso.data.ContextResponse;
import reverso.data.Response;
import reverso.data.SynonymResponse;
import reverso.supportedLanguages.ContextLanguage;
import reverso.supportedLanguages.SynonymLanguage;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

       SynonymResponse response = Reverso.getSynonyms(SynonymLanguage.ENGLISH,"asde");

        System.out.println(response.isOK());
        System.out.println(response.getSynonyms());


        ContextResponse response1 = Reverso.getContext(ContextLanguage.ENGLISH,ContextLanguage.GERMAN,"hello");

       /* System.out.println(response1.isOK());
        System.out.println(response1.getResults());
*/



    }
}