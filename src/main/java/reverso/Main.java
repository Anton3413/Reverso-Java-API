package reverso;

import reverso.data.SynonymResponse;
import reverso.supportedLanguages.SynonymLanguage;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

       SynonymResponse response = Reverso.getSynonyms(SynonymLanguage.ENGLISH,"asde");
        System.out.println(response.isOK());
        System.out.println(response.getSynonyms());

    }
}