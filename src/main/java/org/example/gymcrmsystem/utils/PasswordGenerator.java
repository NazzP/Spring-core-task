package org.example.gymcrmsystem.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PasswordGenerator {

    private final String characters;
    private final int passwordLength;
    private final SecureRandom random;

    public PasswordGenerator(@Value("${password.generation.characters}") String characters,
                             @Value("${password.generation.passwordLength}") int passwordLength) {
        this.characters = characters;
        this.passwordLength = passwordLength;
        this.random = new SecureRandom();
    }

    public String generateRandomPassword() {
        StringBuilder password = new StringBuilder(passwordLength);
        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = random.nextInt(characters.length());
            password.append(characters.charAt(randomIndex));
        }
        return password.toString();
    }
}
