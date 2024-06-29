package reverso;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reverso.data.response.impl.SynonymResponse;
import reverso.supportedLanguages.Language;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class getSynonymsTest {
    private static final Logger logger = Logger.getLogger(getVoiceStreamTest.class.getName());

    SynonymResponse synonymResponse;
    Properties properties;

    @BeforeAll
    void initializeProperties() {
        properties = new Properties();
        try {
            properties.load(Reverso.class.getResourceAsStream("/messages.properties"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error loading properties file", e);
        }
    }

    @Test
    void successSpanishSynonymsResponse(){
        synonymResponse = Reverso.getSynonyms(Language.SPANISH,"increíble");

        assertTrue(synonymResponse.isOK());
        assertNull(synonymResponse.getErrorMessage());
        assertNotNull(synonymResponse.getSynonyms());
        assertTrue(synonymResponse.getSynonyms().get("Adjective").size() > 10);
    }
    @Test
    void successArabicSynonymsRequest(){
        synonymResponse = Reverso.getSynonyms(Language.ARABIC,"جميل");

        assertTrue(synonymResponse.isOK());
        assertNull(synonymResponse.getErrorMessage());
        assertNotNull(synonymResponse.getSynonyms());
        assertTrue(synonymResponse.getSynonyms().get("Adjective").size() > 10);
    }
    @Test
    void failedUnsupportedLanguageRequest(){
        synonymResponse = Reverso.getSynonyms(Language.KOREAN,"아름다운");

        assertFalse(synonymResponse.isOK());
        assertNotNull(synonymResponse.getErrorMessage());
        assertNull(synonymResponse.getSynonyms());
        assertEquals(properties.getProperty("message.error.synonym.unSupportedLanguage"),
                synonymResponse.getErrorMessage());
        assertThrows(NullPointerException.class, () -> synonymResponse.getSynonyms().get("Adjective").size());
    }

    @Test
    void failedNoResultsSynonymsRequest(){
        synonymResponse = Reverso.getSynonyms(Language.GERMAN,"fhbbfbhsd1njdjcnf");

        assertFalse(synonymResponse.isOK());
        assertNotNull(synonymResponse.getErrorMessage());
        assertNull(synonymResponse.getSynonyms());
        assertEquals(properties.getProperty("message.error.synonym.noResults"),synonymResponse.getErrorMessage());
        assertThrows(NullPointerException.class, () -> synonymResponse.getSynonyms().get("Adjective").size());
    }












    @AfterEach
    void initializeInstance(){
        synonymResponse = null;
    }

}
