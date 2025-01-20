package org.example.gymcrmsystem.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.example.gymcrmsystem.config.AppConfig;
import org.example.gymcrmsystem.config.JpaTestConfig;
import org.example.gymcrmsystem.config.TestAppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestAppConfig.class)
@ActiveProfiles("test")
class PasswordGeneratorTest {

    private PasswordGenerator passwordGenerator;

    @Value("${password.generation.characters}")
    private String characters;

    @Value("${password.generation.passwordLength}")
    private int passwordLength;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        passwordGenerator = new PasswordGenerator(characters, passwordLength);
    }

    @Test
    void testGenerateRandomPasswordLength() {
        String password = passwordGenerator.generateRandomPassword();

        assertEquals(passwordLength, password.length(), "Generated password length should be " + passwordLength);
    }

    @Test
    void testGeneratedPasswordContainsValidCharacters() {
        String password = passwordGenerator.generateRandomPassword();

        for (char c : password.toCharArray()) {
            assertTrue(characters.indexOf(c) >= 0, "Generated password contains invalid character: " + c);
        }
    }

    @Test
    void testGenerateRandomPasswordEmptyCharacterSet() {
        String emptyCharacters = "";
        PasswordGenerator emptyCharacterGenerator = new PasswordGenerator(emptyCharacters, passwordLength);

        assertThrows(IllegalArgumentException.class, emptyCharacterGenerator::generateRandomPassword, "Password generation with empty character set should throw an exception");
    }

    @Test
    void testPasswordGenerationWithCustomCharacterSet() {
        String customCharacters = "abc123";
        int customLength = 8;
        PasswordGenerator customGenerator = new PasswordGenerator(customCharacters, customLength);

        String password = customGenerator.generateRandomPassword();

        assertEquals(customLength, password.length(), "Generated password length should be " + customLength);
        for (char c : password.toCharArray()) {
            assertTrue(customCharacters.indexOf(c) >= 0, "Generated password contains invalid character: " + c);
        }
    }

    @Test
    void testPasswordGenerationConsistency() {
        String password1 = passwordGenerator.generateRandomPassword();
        String password2 = passwordGenerator.generateRandomPassword();

        assertNotEquals(password1, password2, "Generated passwords should be different on each call");
    }
}

