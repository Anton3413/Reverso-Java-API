package reverso.data.response.impl;

import com.google.gson.Gson;
import reverso.data.response.Response;
import reverso.data.response.Response1;

import java.util.Map;

public class VoiceResponse extends Response1 {

    private String voiceName;
    private String voiceGender;
    private byte[] mp3Data;

    public VoiceResponse (boolean isOK,String errorMessage,String sourceLanguage,String text,
                               String voiceName,String voiceGender) {
        super(isOK,errorMessage,sourceLanguage,text);
        this.voiceName = voiceName;
        this.voiceGender = voiceGender;
        this.mp3Data = null;
    }

    public VoiceResponse(boolean isOK, String sourceLanguage, String text, String voiceName,
                               String voiceGender,byte[] mp3Data) {
        super(isOK, sourceLanguage, text);
        this.voiceName = voiceName;
        this.voiceGender = voiceGender;
        this.mp3Data = mp3Data;
    }

    public String getVoiceName() {
        return voiceName;
    }

    public String getVoiceGender() {
        return voiceGender;
    }

    public void setVoiceName(String voiceName) {
        this.voiceName = voiceName;
    }

    public void setVoiceGender(String voiceGender) {
        this.voiceGender = voiceGender;
    }

    public byte[] getMp3Data() {
        return mp3Data;
    }

    public void setMp3Data(byte[] mp3Data) {
        this.mp3Data = mp3Data;
    }

}