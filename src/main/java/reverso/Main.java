package reverso;

import reverso.data.response.impl.ContextResponse;
import reverso.data.response.impl.SynonymResponse;
import reverso.data.response.impl.TranslateResponse;
import reverso.data.response.impl.VoiceResponse;
import reverso.supportedLanguages.ContextLanguage;
import reverso.supportedLanguages.SynonymLanguage;
import reverso.supportedLanguages.Voice;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Arrays;

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

       VoiceResponse response =  Reverso.getVoiceStream(Voice.ARABIC_LEILA,"DFAS");

        System.out.println(response.getSourceLanguage());
        System.out.println(response.getVoiceName());
        System.out.println(response.getVoiceGender());
        System.out.println(Arrays.toString(response.getMp3Data()));
        System.out.println(response.getText());
        }
}