package reverso;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reverso.data.response.impl.ConjugationResponse;
import reverso.language.Language;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class getWordConjugationTest {

    private final Logger logger = Logger.getLogger(getVoiceStreamTest.class.getName());
    Reverso reverso;
    ConjugationResponse conjugationResponse;
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
    void SuccessGermanConjugationRequest(){
        conjugationResponse = reverso.getWordConjugation(Language.GERMAN,"haben");

        assertTrue(conjugationResponse.isOK());
        assertNull(conjugationResponse.getErrorMessage());
        assertNotNull(conjugationResponse.getConjugationData());
        assertTrue(conjugationResponse.getConjugationData().size()>10);
    }

    @Test
    void FailedUnsupportedLanguageConjugationRequest(){
        conjugationResponse = reverso.getWordConjugation(Language.POLISH,"Posiadać");

        assertFalse(conjugationResponse.isOK());
        assertNotNull(conjugationResponse.getErrorMessage());
        assertNull(conjugationResponse.getConjugationData());
        assertEquals(properties.getProperty("message.error.conjugation.invalidLanguage"),
                conjugationResponse.getErrorMessage());
    }

    @Test
    void FailedIncorrectTextConjugationRequest(){
        conjugationResponse = reverso.getWordConjugation(Language.HEBREW,"randomtextrandom");

        assertFalse(conjugationResponse.isOK());
        assertNotNull(conjugationResponse.getErrorMessage());
        assertNull(conjugationResponse.getConjugationData());
        assertEquals(properties.getProperty("message.error.conjugation.incorrectWord"),
                conjugationResponse.getErrorMessage());
    }

    @AfterEach
    void initializeInstance(){
        conjugationResponse = null;
    }
}
