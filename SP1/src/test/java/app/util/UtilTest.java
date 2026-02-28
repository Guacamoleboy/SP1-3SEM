package app.util;

import app.exception.ResourceNotFoundException;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UtilTest {

    // Attributes
    private static final String TEST_RESOURCE = "test.properties";
    private static final String TEST_RESOURCE_FAIL = "tes.properties";
    private static String identifier = "";

    // _________________________________________________________________

    @BeforeAll
    public static void setup() {
        Util.setTestMode(true);
    }

    // _________________________________________________________________

    @BeforeEach
    public void beforeEachSetup(){
        identifier = "";
    }

    // _________________________________________________________________

    @Test
    @DisplayName("Making sure whitespace is trimmed on return")
    public void shouldTrimWhitespace(){
        // Arrange
        identifier = "DB_NAME_UNTRIMMED";
        // Act
        String value = Util.getPropertyValue(identifier, TEST_RESOURCE);
        // Assert
        assertEquals("test", value);
    }

    // _________________________________________________________________

    @Test
    @DisplayName("Making sure our value matches the return (+- same as the one above)")
    public void shouldReturnValue(){
        // Arrange
        identifier = "DB_NAME_NORMAL";
        // Act
        String value = Util.getPropertyValue(identifier, TEST_RESOURCE);
        // Assert
        assertEquals("test", value);
    }

    // _________________________________________________________________

    @Test
    @DisplayName("In case value doesn't exist but row does.. Throw ResourceNotFoundException")
    void valueDoesntExist() {
        // Arrange
        identifier = "THIS_IS_NOT_AN_IDENTIFIER";
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () ->
            Util.getPropertyValue(identifier, TEST_RESOURCE)
        );
    }

    // _________________________________________________________________

    @Test
    @DisplayName("File missing? Throw ResourceNotFoundException")
    void shouldThrowExceptionFileMissing() {
        // Arrange
        identifier = "THIS_IS_NOT_AN_IDENTIFIER";
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () ->
                Util.getPropertyValue(identifier, TEST_RESOURCE_FAIL)
        );
    }

    // _________________________________________________________________

    @Test
    @DisplayName("File missing but identifier exist in correct? Throw ResourceNotFoundException")
    void shouldThrowExceptionFileMissingIdenfifierCorrect() {
        // Arrange
        identifier = "DB_NAME_NORMAL";
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () ->
                Util.getPropertyValue(identifier, TEST_RESOURCE_FAIL)
        );
    }

    // _________________________________________________________________

    @Test
    @DisplayName("Check for special characters")
    public void shouldHandleSpecialCharacters() {
        // Arrange
        identifier = "DB_NAME_CHARACTERS";
        // Act
        String value = Util.getPropertyValue(identifier, TEST_RESOURCE);
        // Assert
        assertEquals("ÆØÅ606019/&.,-", value);
    }

    // _________________________________________________________________

    @Test
    @DisplayName("fileMissingSearcher should find test.properties")
    public void shouldFindSuggestions() {
        // Arrange
        String searchParam = TEST_RESOURCE_FAIL;
        // Act
        List<String> suggestions = Util.fileMissingSearcher(searchParam);
        // Assert
        assertFalse(suggestions.isEmpty());
        assertTrue(suggestions.contains(TEST_RESOURCE));
    }

    // _________________________________________________________________

    @Test
    @DisplayName("Test resource file can be loaded successfully")
    public void shouldLoadPropertiesFile() {
        // Arrange
        identifier = "DB_NAME_NORMAL";
        // Act
        String value = Util.getPropertyValue(identifier, TEST_RESOURCE);
        // Assert
        assertNotNull(value);
    }

    // _________________________________________________________________

    @Test
    @DisplayName("fileMissingSearcher returns empty list if no properties files exist in folder & no file is correct")
    public void shouldReturnEmptyOnNoMatch() {
        // Arrange
        String searchParam = "no_match_at_all";
        // Act
        List<String> suggestions = Util.fileMissingSearcher(searchParam);
        // Assert
        assertNotNull(suggestions);
    }

    // _________________________________________________________________

    @Test
    @DisplayName("Empty value should return empty String")
    public void shouldReturnEmptyString() {
        // Arrange
        identifier = "EMPTY_VALUE";
        // Act
        String value = Util.getPropertyValue(identifier, TEST_RESOURCE);
        // Assert
        assertEquals("", value);
    }

    // _________________________________________________________________

    @Test
    @DisplayName("Quotes and value should be preserved as-is")
    public void shouldPreserveQuotes() {
        // Arrange
        identifier = "QUOTE_TEST";
        // Act
        String value = Util.getPropertyValue(identifier, TEST_RESOURCE);
        // Assert
        assertEquals("\"this is a quote\"", value);
    }

    // _________________________________________________________________

    @Test
    @DisplayName("Parentheses should be kept along with the value")
    public void shouldPreserveParentheses() {
        // Arrange
        identifier = "PARENTHESES_TEST";
        // Act
        String value = Util.getPropertyValue(identifier, TEST_RESOURCE);
        // Assert
        assertEquals("(this is a text)", value);
    }

    // _________________________________________________________________

    @Test
    @DisplayName("Line break testing. Expected behavior is as-is. No reformatting")
    public void shouldHandleLineBreakCharacters() {
        // Arrange
        identifier = "LINE_BREAK_CHARACTER";
        // Act
        String value = Util.getPropertyValue(identifier, TEST_RESOURCE);
        // Assert
        assertEquals("\\n", value);
    }

}