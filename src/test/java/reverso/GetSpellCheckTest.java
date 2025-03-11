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
    SpellCheckResponse response;
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

        String englishText = "here ve have errores, we shuld to fix et.";
        response = reverso.getSpellCheck(Language.ENGLISH,englishText);

        assertTrue(response.isOK());
        assertNull(response.getErrorMessage());
        assertNotEquals(englishText, response.getCorrectedTextAsString());
        assertNotNull(response.getStats());
    }

    @Test
    void successFrenchSpellCheckRequest() {

        String frenchText = "Je sui arivé a l'otel en Paris, é j'aim bocoup moin la vil.";

        response = reverso.getSpellCheck(Language.FRENCH,frenchText);

        assertTrue(response.isOK());
        assertNull(response.getErrorMessage());
        assertNotEquals(frenchText, response.getCorrectedTextAsString());
        assertNotNull(response.getStats());
    }

    @Test
    void successEnglishSpellCheckRequest2(){
        String englishText = "Yesterday I go to the market."+
                "I buy some apple and a bottel of milk." +
                "Then I meet my friend and we drink cofee together.";

        response = reverso.getSpellCheck(Language.ENGLISH,englishText);

        assertTrue(response.isOK());
        assertNull(response.getErrorMessage());
        assertNotEquals(englishText, response.getCorrectedTextAsString());
        assertEquals(25,response.getStats().getWordCount());
        assertEquals(response.getStats().toJson(),response.getStats().toString());
    }

    @Test
    void successItalianSpellCheckRequest(){
        String italianText = "Certto, eccomi!. " +
                "Io amo mangiare piza con fromaggio.";

        response = reverso.getSpellCheck(Language.ITALIAN,italianText);

        assertTrue(response.isOK());
        assertNull(response.getErrorMessage());
        assertNotEquals(italianText, response.getCorrectedTextAsString());
        assertTrue(response.getStats().getWordCount()>5);
        assertEquals(response.getStats().toJson(),response.getStats().toString());
    }


    @Test
    void failedUnsupportedRussianSpellCheckRequest() {

        response = reverso.getSpellCheck(Language.RUSSIAN,"good luck");

        assertFalse(response.isOK());
        assertNotNull(response.getErrorMessage());
        assertEquals(properties.getProperty("message.error.spellCheck.unsupportedLanguage"), response.getErrorMessage());
        assertNull(response.getCorrectedTextAsString());
    }

    @Test
    void failedUnsupportedSwedishSpellCheckRequest() {

        response = reverso.getSpellCheck(Language.SWEDISH,"Jag gilar att äta fisk och potatis");

        assertFalse(response.isOK());
        assertNotNull(response.getErrorMessage());
        assertEquals(properties.getProperty("message.error.spellCheck.unsupportedLanguage"), response.getErrorMessage());
        assertNull(response.getCorrectedTextAsString());
        assertNull(response.getStats());
    }

    @Test
    void failedSpanishNoMistakesOnTextSpellCheckRequest() {

        response = reverso.getSpellCheck(Language.SPANISH,"hola");

        assertFalse(response.isOK());
        assertNotNull(response.getErrorMessage());
        assertEquals(properties.getProperty("message.error.spellCheck.noErrorsOrMismatchedLanguage"), response.getErrorMessage());
    }

    @AfterEach
    void initializeInstance(){
        response = null;
    }

    @Test
    void failedUnsupportedArabicSpellCheckRequest() {

        response = reverso.getSpellCheck(Language.RUSSIAN,"Я желать тебе крепкава здаровя");

        assertFalse(response.isOK());
        assertNotNull(response.getErrorMessage());
        assertEquals(properties.getProperty("message.error.spellCheck.unsupportedLanguage"), response.getErrorMessage());
        assertNull(response.getCorrectedTextAsString());
    }
}
