package reverso;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reverso.data.response.impl.ConjugationResponse;
import reverso.supportedLanguages.Language;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class getWordConjugationTest {

    private static final Logger logger = Logger.getLogger(getVoiceStreamTest.class.getName());

    ConjugationResponse conjugationResponse;
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
    void SuccessEnglishConjugationRequest(){
        conjugationResponse = Reverso.getWordConjugation(Language.GERMAN,"haben");

        assertTrue(conjugationResponse.isOK());
        assertNull(conjugationResponse.getErrorMessage());
        assertNotNull(conjugationResponse.getConjugationData());
        assertTrue(conjugationResponse.getConjugationData().size()>10);
    }

    @AfterEach
    void initializeInstance(){
        conjugationResponse = null;
    }
}
