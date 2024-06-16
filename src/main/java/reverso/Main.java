package reverso;

import reverso.data.response.impl.SynonymResponse;
import reverso.supportedLanguages.Language;
import reverso.supportedLanguages.Voice;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

       /* SynonymResponse response = Reverso.getSynonyms(SynonymLanguage.ENGLISH, "asde");

        System.out.println(response.isOK());
        System.out.println(response.getSynonyms());

        ContextResponse response1 = Reverso.getContext(ContextLanguage.ENGLISH, ContextLanguage.GERMAN, "оитотот");


            TranslateResponse translateResponse = Reverso.getTranslations(SynonymLanguage.ENGLISH, SynonymLanguage.RUSSIAN,
                    "probaply");


            System.out.println(translateResponse.getSourceLanguage());
            System.out.println(translateResponse.getText());
            System.out.println(translateResponse.getTargetLanguage());
            System.out.println(translateResponse.getTranslatedText());
            System.out.println(translateResponse.getContextTranslations());



            Reverso.getWordConjugation(ContextLanguage.ARABIC,"жопа");*/

       /*VoiceResponse response =  Reverso.getVoiceStream(Voice.ARABIC_LEILA,"DFAS");

        System.out.println(response.getSourceLanguage());
        System.out.println(response.getVoiceName());
        System.out.println(response.getVoiceGender());
        System.out.println(Arrays.toString(response.getMp3Data()));
        System.out.println(response.getText());*/

        byte[] array = Reverso.getVoiceStream(Voice.BRITISH_QUEEN_ELIZABETH,"Hello!").getMp3Data();

        System.out.println(array.length);

        SynonymResponse response = Reverso.getSynonyms(Language.ENGLISH,"hello");

        if(!response.isOK()) {
            System.out.println(response.getErrorMessage());
        }
        else {response.setSynonyms(null);
            System.out.println(response.getAsJson());}
        }
}