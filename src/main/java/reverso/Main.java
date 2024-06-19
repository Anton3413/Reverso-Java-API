package reverso;

import com.google.gson.Gson;
import reverso.data.response.impl.VoiceResponse;
import reverso.supportedLanguages.Voice;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {

        VoiceResponse response = Reverso.getVoiceStream(Voice.ENGLISH_LISA, "hello everyone");

        System.out.println(new Gson().toJson(response));
    }
}