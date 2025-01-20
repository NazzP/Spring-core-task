package org.example.gymcrmsystem.utils;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.gymcrmsystem.config.AppConfig;
import org.example.gymcrmsystem.config.JpaTestConfig;
import org.example.gymcrmsystem.config.TestAppConfig;
import org.example.gymcrmsystem.dto.UserDto;
import org.example.gymcrmsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestAppConfig.class)
@ActiveProfiles("test")
class UsernameGeneratorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UsernameGenerator usernameGenerator;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDto = UserDto.builder()
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .build();
    }

    @Test
    void testGenerateUniqueUsernameWhenUsernameDoesNotExist() {
        String expectedUsername = "John.Doe";
        when(userRepository.existsByUsername(expectedUsername)).thenReturn(false);

        String username = usernameGenerator.generateUniqueUsername(userDto);

        assertEquals(expectedUsername, username);
        verify(userRepository, times(1)).existsByUsername(expectedUsername);
    }

    @Test
    void testGenerateUniqueUsernameWhenUsernameExistsOnce() {
        String baseUsername = "John.Doe";
        String expectedUsername = baseUsername + "1";
        when(userRepository.existsByUsername(baseUsername)).thenReturn(true);
        when(userRepository.existsByUsername(expectedUsername)).thenReturn(false);

        String username = usernameGenerator.generateUniqueUsername(userDto);

        assertEquals(expectedUsername, username);
        verify(userRepository, times(2)).existsByUsername(anyString());
    }

    @Test
    void testGenerateUniqueUsernameWhenUsernameExistsMultipleTimes() {
        String baseUsername = "John.Doe";
        String username1 = baseUsername + "1";
        String username2 = baseUsername + "2";
        String expectedUsername = baseUsername + "3";

        when(userRepository.existsByUsername(baseUsername)).thenReturn(true);
        when(userRepository.existsByUsername(username1)).thenReturn(true);
        when(userRepository.existsByUsername(username2)).thenReturn(true);
        when(userRepository.existsByUsername(expectedUsername)).thenReturn(false);

        String username = usernameGenerator.generateUniqueUsername(userDto);

        assertEquals(expectedUsername, username);
        verify(userRepository, times(4)).existsByUsername(anyString());
    }

    @Test
    void testCheckIfUsernameExists() {
        String username = "John.Doe";

        boolean exists = usernameGenerator.checkIfUsernameExists(username);
        assertFalse(exists);

        when(userRepository.existsByUsername(username)).thenReturn(true);
        exists = usernameGenerator.checkIfUsernameExists(username);
        assertTrue(exists);

        verify(userRepository, times(2)).existsByUsername(username);
    }

    @Test
    void testCheckIfUsernameExistsWhenNotExist() {
        String username = "John.Doe";
        when(userRepository.existsByUsername(username)).thenReturn(false);

        boolean exists = usernameGenerator.checkIfUsernameExists(username);

        assertFalse(exists);
        verify(userRepository, times(1)).existsByUsername(username);
    }
}

