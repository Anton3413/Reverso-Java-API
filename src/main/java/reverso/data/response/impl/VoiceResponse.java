package reverso.data.response.impl;

import reverso.data.response.Response;

import java.util.Base64;
import java.util.Map;

public class VoiceResponse extends Response {

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

    public String getAudioAsBase64() {
        if(mp3Data == null) {
            throw new NullPointerException("mp3Data is null");
        }
        return Base64.getEncoder().encodeToString(mp3Data);
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

    @Override
    protected void addCustomFields(Map<String, Object> jsonMap) {
        jsonMap.put("voiceName", voiceName);
        jsonMap.put("voiceGender", voiceGender);
    }

    @Override
    public String toString() {
        return "{\n" +
                "  \"isOK\": " + isOK() + ",\n" +
                "  \"sourceLanguage\": \"" + getSourceLanguage() + "\",\n" +
                "  \"text\": \"" + getText() + "\",\n" +
                "  \"errorMessage\": \"" + getErrorMessage() + "\",\n" +
                "  \"voiceName\": \"" + voiceName + "\",\n" +
                "  \"voiceGender\": \"" + voiceGender + "\",\n" +
                "  \"mp3DataBase64\": \"" + (mp3Data!=null) + "\"\n" +
                "}";
    }

}