package reverso;


import reverso.data.response.impl.*;
import reverso.supportedLanguages.Language;
import reverso.supportedLanguages.Voice;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        /*VoiceResponse response = Reverso.getVoiceStream(Voice.MANDARIN_CHINESE_LULU,"你好你ddf好嗎ll？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？你好你好嗎？");
        System.out.println(response.toJson());

        if(!response.isOK()){
            return;
        }
        File file = new File("D:\\TETE\\file1.mp3");
        try(FileOutputStream fos = new FileOutputStream(file)){
            if(!file.exists()){
                file.createNewFile();
            }
            fos.write(response.getMp3Data());
            if (!Desktop.isDesktopSupported()) {
                System.out.println("Desktop API не поддерживается.");
                return;
            }

            Desktop desktop = Desktop.getDesktop();

            try {
                // Открываем файл с помощью стандартного приложения по умолчанию
                desktop.open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

       /* SynonymResponse response = Reverso.getSynonyms(Language.SPANISH,"increíble");

        if(response.isOK()) {
            System.out.println(response.toJson());
        }else System.out.println(response.getErrorMessage());
*/
        /*ContextResponse contextResponse = Reverso.getContext(Language.UKRAINIAN,Language.JAPANESE,"яблуко");
        System.out.println(contextResponse.toJson());
        System.out.println(contextResponse.getContextResults());*/

        ConjugationResponse conjugationResponse = Reverso.getWordConjugation(Language.RUSSIAN,"идти");

        if(conjugationResponse.isOK()){
            System.out.println(conjugationResponse.toJson());
        }
    }
}