package main.najah.test;

import main.najah.code.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class UserServiceTest {

    UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    @DisplayName("Should validate correct email format")
    void testValidEmail() {
        assertAll(
            () -> assertTrue(userService.isValidEmail("Mohammed@gmail.com")),
            () -> assertTrue(userService.isValidEmail("user.name@domain.co"))
        );
    }

    @Test
    @DisplayName("Should detect invalid email format")
    void testInvalidEmail() {
        assertAll(
            () -> assertFalse(userService.isValidEmail("215cdcs-email")),
            () -> assertFalse(userService.isValidEmail(null)),
            () -> assertFalse(userService.isValidEmail(""))
        );
    }

    @Test
    @DisplayName("Should authenticate correct credentials")
    void testCorrectAuth() {
        assertTrue(userService.authenticate("admin", "1234"));
    }

    @Test
    @DisplayName("Should fail authentication with wrong credentials")
    void testWrongAuth() {
        assertAll(
            () -> assertFalse(userService.authenticate("admin", "wrong")),
            () -> assertFalse(userService.authenticate("user", "1234")),
            () -> assertFalse(userService.authenticate("", ""))
        );
    }

    @ParameterizedTest
    @CsvSource({
        "admin, 1234, true",
        "admin, wrong, false",
        "user, 1234, false",
        "kkkk, kkkk, false"
    })
    @DisplayName("Parameterized test for multiple credentials")
    void testAuthParameterized(String username, String password, boolean expected) {
        assertEquals(expected, userService.authenticate(username, password));
    }

    @Test
    @Timeout(1)
    @DisplayName("Email validation should complete within 1 second")
    void testEmailValidationTimeout() {
        assertTrue(userService.isValidEmail("fastcheck@domain.com"));
    }
}