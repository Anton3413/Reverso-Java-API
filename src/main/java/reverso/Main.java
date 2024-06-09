package reverso;

import reverso.data.response.ContextResponse;
import reverso.data.response.SynonymResponse;
import reverso.data.response.TranslateResponse;
import reverso.supportedLanguages.ContextLanguage;
import reverso.supportedLanguages.SynonymLanguage;
import reverso.supportedLanguages.Voice;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {

       SynonymResponse response = Reverso.getSynonyms(SynonymLanguage.ENGLISH,"asde");

        System.out.println(response.isOK());
        System.out.println(response.getSynonyms());

        ContextResponse response1 = Reverso.getContext(ContextLanguage.ENGLISH,ContextLanguage.GERMAN,"оитотот");

       /*for(Map.Entry<String,String> element : response1.getResults().entrySet()){
           System.out.println(element.getKey() + " : " + element.getValue());
       }*/

        TranslateResponse translateResponse = Reverso.getTranslations(SynonymLanguage.ENGLISH, SynonymLanguage.RUSSIAN,
                "probaply");


            System.out.println(translateResponse.getSourceLanguage());
            System.out.println(translateResponse.getWord());
            System.out.println(translateResponse.getTargetLanguage());
            System.out.println(translateResponse.getTranslatedText());
            System.out.println(translateResponse.getContextTranslations());


        byte[] audioBytes =     Reverso.getVoiceStream(Voice.BRITISH_GRAHAM,"hello");

    }
}