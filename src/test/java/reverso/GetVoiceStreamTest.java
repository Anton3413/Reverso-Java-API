package reverso;

import org.junit.jupiter.api.*;
import reverso.data.response.impl.VoiceResponse;
import reverso.language.Voice;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetVoiceStreamTest {
    private static final Logger logger = Logger.getLogger(GetVoiceStreamTest.class.getName());
    Reverso reverso;
    VoiceResponse voiceResponse;
    Properties properties;

    @BeforeAll
    void initializeReversoAndProperties() {
        reverso = new Reverso();
        properties = new Properties();
        try {
            properties.load(Reverso.class.getResourceAsStream("/messages.properties"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error loading properties file", e);
        }
    }

    @Test
    void successRandomEnglishRequest(){
        voiceResponse = reverso.getVoiceStream(Voice.getRandomEnglish(),"Big Brother is watching you.");
        assertTrue(voiceResponse.isOK());
        assertDoesNotThrow(() ->voiceResponse.getAudioAsBase64());
        assertNull(voiceResponse.getErrorMessage());
        assertNotNull(voiceResponse.getMp3Data());
    }
    @Test
    void failureDifferentVoiceAndTextRequest(){
        voiceResponse = reverso.getVoiceStream(Voice.ARABIC_LEILA,"你好！");
        assertFalse(voiceResponse.isOK());
        assertNull(voiceResponse.getMp3Data());
        assertNotNull(voiceResponse.getErrorMessage());
        assertEquals(properties.getProperty("message.error.voiceStream.9358BytesResponse"),
                voiceResponse.getErrorMessage());
    }

    @Test
    void failureChineseTextTooLongRequest(){
        String longText = "你好！".repeat(300);

        voiceResponse = reverso.getVoiceStream(Voice.MANDARIN_CHINESE_LULU,longText);

        assertFalse(voiceResponse.isOK());
        assertNull(voiceResponse.getMp3Data());

    }

    @AfterEach
    void initializeInstance(){
        voiceResponse = null;
    }
}
