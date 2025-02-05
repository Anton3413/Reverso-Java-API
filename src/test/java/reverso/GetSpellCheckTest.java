package reverso;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reverso.data.response.impl.SpellCheckResponse;
import reverso.language.Language;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetSpellCheckTest {

    private final Logger logger = Logger.getLogger(GetVoiceStreamTest.class.getName());

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
    void successEnglishSpellCheckRequest() {

        String englishText = "here ve have errores, we shuld to fix et";
        SpellCheckResponse response = reverso.getSpellCheck(Language.ENGLISH,englishText);

        assertTrue(response.isOK());
        assertNull(response.getErrorMessage());
        assertNotNull(response.getCorrectedTextAsString());
        assertNotEquals(englishText, response.getCorrectedTextAsString());
        assertNotNull(response.getStats());
    }

    @Test
    void successFrenchSpellCheckRequest() {

        String frenchText = "Je sui arivé a l'otel en Paris, é j'aim bocoup moin la vil.";

        SpellCheckResponse response = reverso.getSpellCheck(Language.FRENCH,frenchText);

        assertTrue(response.isOK());
        assertNull(response.getErrorMessage());
        assertNotNull(response.getCorrectedTextAsString());
        assertNotEquals(frenchText, response.getCorrectedTextAsString());
        assertNotNull(response.getStats());
    }


    @Test
    void failedUnsupportedRussianSpellCheckRequest() {

        SpellCheckResponse response = reverso.getSpellCheck(Language.RUSSIAN,"good luck");

        assertFalse(response.isOK());
        assertNotNull(response.getErrorMessage());
        assertEquals(properties.getProperty("message.error.spellCheck.unsupportedLanguage"), response.getErrorMessage());
        assertNull(response.getCorrectedTextAsString());
    }

    @Test
    void failedNoMistakesOnTextSpellCheckRequest() {

        SpellCheckResponse response = reverso.getSpellCheck(Language.SPANISH,"hola");

        assertFalse(response.isOK());
        assertNotNull(response.getErrorMessage());
        assertEquals(properties.getProperty("message.error.spellCheck.noErrorsOrMismatchedLanguage"), response.getErrorMessage());
    }

    @AfterEach
    void initializeInstance(){
        spellCheckResponse = null;
    }
}
