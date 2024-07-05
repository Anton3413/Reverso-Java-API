package reverso;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reverso.data.response.impl.SpellCheckResponse;
import reverso.supportedLanguages.Language;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class getSpellCheck {

    private final Logger logger = Logger.getLogger(getVoiceStreamTest.class.getName());

    Reverso reverso;
    SpellCheckResponse spellCheckResponse;
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
    void SuccessEnglishSpellCheckRequest() {

        String englishText = "here ve have errores, we shuld to fix et";
        SpellCheckResponse response = reverso.getSpellCheck(Language.ENGLISH,englishText);

        assertTrue(response.isOK());
        assertNull(response.getErrorMessage());
        assertNotNull(response.getCorrectedText());
        assertNotEquals(englishText, response.getCorrectedText());
        assertNotNull(response.getStats());
    }

    @AfterEach
    void initializeInstance(){
        spellCheckResponse = null;
    }
}
