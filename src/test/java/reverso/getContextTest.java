package reverso;

import org.junit.jupiter.api.*;
import reverso.data.response.impl.ContextResponse;
import reverso.supportedLanguages.Language;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class getContextTest {
    private final Logger logger = Logger.getLogger(getVoiceStreamTest.class.getName());

    Reverso reverso;
    ContextResponse contextResponse;
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
    void successRussianEnglishContextRequest(){
        contextResponse = reverso.getContext(Language.ENGLISH,Language.RUSSIAN,"красивый");

        assertTrue(contextResponse.isOK());
        assertNull(contextResponse.getErrorMessage());
        assertEquals(Language.ENGLISH.getFullName(),contextResponse.getSourceLanguage());
        assertNotNull(contextResponse.getContextResults());
        assertTrue(contextResponse.getContextResults().size()>15);
        assertTrue(contextResponse.getTranslations().length>5);
    }

    @Test
    void successArabicItalianContextRequest(){
        contextResponse = reverso.getContext(Language.ARABIC,Language.ITALIAN,"جميل");

        assertTrue(contextResponse.isOK());
        assertNull(contextResponse.getErrorMessage());
        assertEquals(Language.ARABIC.getFullName(),contextResponse.getSourceLanguage());
        assertNotNull(contextResponse.getContextResults());
        assertTrue(contextResponse.getContextResults().size()>15);
        assertTrue(contextResponse.getTranslations().length>5);
    }

    @Test
    void FailureUnsupportedKoreanUkrainianContextRequest(){
        contextResponse = reverso.getContext(Language.KOREAN,Language.UKRAINIAN,"красивий");

        assertFalse(contextResponse.isOK());
        assertNotNull(contextResponse.getErrorMessage());
        assertEquals(Language.UKRAINIAN.getFullName(),contextResponse.getTargetLanguage());
        assertNull(contextResponse.getContextResults());
        assertEquals(properties.getProperty("message.error.context.UnsupportedLanguages"),
                contextResponse.getErrorMessage());
    }

    @Test
    void FailureUnsupportedTurkishPolishContextRequest(){
        contextResponse = reverso.getContext(Language.TURKISH,Language.POLISH,"Güzel");

        assertFalse(contextResponse.isOK());
        assertNotNull(contextResponse.getErrorMessage());
        assertEquals(Language.POLISH.getFullName(),contextResponse.getTargetLanguage());
        assertNull(contextResponse.getContextResults());
        assertNull(contextResponse.getTranslations());
        assertEquals(properties.getProperty("message.error.context.UnsupportedLanguages"),
                contextResponse.getErrorMessage());
    }

    @Test
    void FailureIncorrectWordContextRequest(){
        contextResponse = reverso.getContext(Language.GERMAN, Language.RUSSIAN,"encaiidanfoafkfffa");

        assertFalse(contextResponse.isOK());
        assertNotNull(contextResponse.getErrorMessage());
        assertEquals(Language.RUSSIAN.getFullName(),contextResponse.getTargetLanguage());
        assertNull(contextResponse.getContextResults());
        assertNull(contextResponse.getTranslations());
        assertEquals(properties.getProperty("message.error.context.noResults"),
                contextResponse.getErrorMessage());
    }
    @Test
    void FailureSameLanguagesContextRequest(){
        contextResponse = reverso.getContext(Language.POLISH,Language.POLISH,"ja pierdole bobr");

        assertFalse(contextResponse.isOK());
        assertNotNull(contextResponse.getErrorMessage());
        assertEquals(contextResponse.getSourceLanguage(),contextResponse.getTargetLanguage());
        assertNull(contextResponse.getContextResults());
        assertNull(contextResponse.getTranslations());
        assertEquals(properties.getProperty("message.error.context.sameLanguage"),contextResponse.getErrorMessage());
    }

    @AfterEach
    void initializeInstance(){
        contextResponse = null;
    }
}
