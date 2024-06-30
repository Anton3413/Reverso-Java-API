package reverso;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reverso.data.response.impl.ContextResponse;
import reverso.supportedLanguages.Language;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class getContextTest {
    private static final Logger logger = Logger.getLogger(getVoiceStreamTest.class.getName());

    ContextResponse contextResponse;
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
    void successRussianEnglishContextRequest(){
        contextResponse = Reverso.getContext(Language.ENGLISH,Language.RUSSIAN,"красивый");

        assertTrue(contextResponse.isOK());
        assertNull(contextResponse.getErrorMessage());
        assertEquals(Language.ENGLISH.getFullName(),contextResponse.getSourceLanguage());
        assertNotNull(contextResponse.getContextResults());
        assertTrue(contextResponse.getContextResults().size()>15);
    }

    @Test
    void successArabicItalianContextRequest(){
        contextResponse = Reverso.getContext(Language.ARABIC,Language.ITALIAN,"جميل");

        assertTrue(contextResponse.isOK());
        assertNull(contextResponse.getErrorMessage());
        assertEquals(Language.ARABIC.getFullName(),contextResponse.getSourceLanguage());
        assertNotNull(contextResponse.getContextResults());
        assertTrue(contextResponse.getContextResults().size()>15);
    }

    @Test
    void FailureUnsupportedKoreanUkrainianContextRequest(){
        contextResponse = Reverso.getContext(Language.KOREAN,Language.UKRAINIAN,"красивий");

        assertFalse(contextResponse.isOK());
        assertNotNull(contextResponse.getErrorMessage());
        assertEquals(Language.UKRAINIAN.getFullName(),contextResponse.getTargetLanguage());
        assertNull(contextResponse.getContextResults());
        assertEquals(properties.getProperty("message.error.context.UnsupportedLanguages"),
                contextResponse.getErrorMessage());
    }

    @Test
    void FailureUnsupportedTurkishPolishContextRequest(){
        contextResponse = Reverso.getContext(Language.TURKISH,Language.POLISH,"Güzel");

        assertFalse(contextResponse.isOK());
        assertNotNull(contextResponse.getErrorMessage());
        assertEquals(Language.POLISH.getFullName(),contextResponse.getTargetLanguage());
        assertNull(contextResponse.getContextResults());
        assertEquals(properties.getProperty("message.error.context.UnsupportedLanguages"),
                contextResponse.getErrorMessage());
    }

    @Test
    void FailureIncorrectWordContextRequest(){
        contextResponse = Reverso.getContext(Language.GERMAN, Language.RUSSIAN,"encaiidanfoafkfffa");

        assertFalse(contextResponse.isOK());
        assertNotNull(contextResponse.getErrorMessage());
        assertEquals(Language.RUSSIAN.getFullName(),contextResponse.getTargetLanguage());
        assertNull(contextResponse.getContextResults());
        assertEquals(properties.getProperty("message.error.context.noResults"),
                contextResponse.getErrorMessage());
    }

    @AfterEach
    void initializeInstance(){
        contextResponse = null;
    }
}
