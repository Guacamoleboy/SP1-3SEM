package app.util;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BCryptHashTest  {

    // Attributes

    // ______________________________________________________

    @Test
    @DisplayName("Should hash a password")
    public void shouldHashPassword() {
        // Arrange
        String password = "password123!";
        // Act
        String hashedPassword = BCryptHash.hash(password);
        // Assert
        assertNotNull(hashedPassword);
        assertTrue(hashedPassword.startsWith("$2a$10$"));
    }

    // ______________________________________________________

    @Test
    @DisplayName("Should confirm password input matches already stored hashed password")
    public void shouldVerifyPassword() {
        // Arrange
        String password = "securePassword";
        String hashedPassword = BCryptHash.hash(password);
        // Act
        boolean passwordsMatch = BCryptHash.check(password, hashedPassword);
        // Assert
        assertTrue(passwordsMatch);
    }

    // ______________________________________________________

    @Test
    @DisplayName("Should fail if wrong password")
    public void shouldFailOnWrongPassword() {
        // Arrange
        String correctPassword = "correctPassword";
        String wrongPassword = "wrongPassword";
        String hashedPassword = BCryptHash.hash(correctPassword);
        // Act
        boolean passwordsMatch = BCryptHash.check(wrongPassword, hashedPassword);
        // Assert
        assertFalse(passwordsMatch);
    }

    // ______________________________________________________

    @Test
    @DisplayName("Should throw exception if password is insane")
    public void shouldThrowOnInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            BCryptHash.hash(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            BCryptHash.hash("");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            BCryptHash.hash("   ");
        });
    }

    // ______________________________________________________

    @Test
    @DisplayName("Should handle very long / odd passwords (72 bytes)")
    public void shouldHandleLongPassword() {
        // Arrange
        String longPassword = "a".repeat(73);
        // Debug Output
        System.out.println("Long Password | Bytes: " + longPassword.getBytes().length);
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            BCryptHash.hash(longPassword);
        });
    }

    // ______________________________________________________

    @Test
    @DisplayName("Should throw exception if emoji spam exceeds 72 bytes")
    public void shouldThrowOnEmojiSpam() {
        // Arrange
        String emojiSpam = "\uD83E\uDD21".repeat(30);
        // Debug Output
        System.out.println("Emoji Spam Bytes: " + emojiSpam.getBytes().length);
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            BCryptHash.hash(emojiSpam);
        });
    }

    // ______________________________________________________

    @Test
    @DisplayName("Should handle special characters and emojis")
    public void shouldHandleSpecialChars() {
        // Arrange
        String specialPassword = "ÆØÅ_!@#$ÆØÅ%^&*(ÆØÅ)_";
        // Debug Output
        System.out.println("Special Password Bytes: " + specialPassword.getBytes().length);
        // Act
        String hashedPassword = BCryptHash.hash(specialPassword);
        // Assert
        assertTrue(BCryptHash.check(specialPassword, hashedPassword));;
    }

    // ______________________________________________________

    @Test
    @DisplayName("Should fail with 71 chars + 1 UTF-8 Char (Total 73 bytes)")
    public void shouldFailOn71BytesWithUTF8() {
        // Arrange
        String password = "a".repeat(71) + "Æ";
        // Debug Output
        System.out.println("71 chars + 1 x UTF-8 | Bytes: " + password.getBytes().length);
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            BCryptHash.hash(password);
        });
    }

    // ______________________________________________________

    @Test
    @DisplayName("Should fail with 69 chars + 1 Emoji (Total 73 bytes)")
    public void shouldFailOn69BytesWithEmoji() {
        // Arrange
        String password = "a".repeat(69) + "\uD83E\uDD21";
        // Debug Output
        System.out.println("68 chars + 1 x Emoji | Bytes: " + password.getBytes().length);
        // Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            BCryptHash.hash(password);
        });
    }

    // ______________________________________________________

    @Test
    @DisplayName("Should create different passwords even though they are the same due to salt gen")
    public void shouldHaveUniqueSalts() {
        // Arrange
        String password = "password123";
        // Act
        String hashedPassword1 = BCryptHash.hash(password);
        String hashedPassword2 = BCryptHash.hash(password);
        // Assert
        assertNotEquals(hashedPassword1, hashedPassword2);
    }

}