package main.najah.test;

import main.najah.code.Calculator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
@Execution(ExecutionMode.CONCURRENT)
class CalculatorTest {
    static Calculator calculator;

    @BeforeAll
    static void initAll() {
        System.out.println("Start Test class");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("End  Test class");
    }

    @BeforeEach 
    void setUp() {
        calculator = new Calculator();
        System.out.println("Start");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test finished");
    }

    @Order(1)
    @Test
    @DisplayName("Addition with positive, negative, and empty input")
    void testAdd() {
        assertAll(
            () -> assertEquals(10, calculator.add(1, 2, 3, 4)),
            () -> assertEquals(0, calculator.add()),
            () -> assertEquals(-6, calculator.add(-1, -2, -3))
        );
    }

    @Order(2)
    @Test
    @DisplayName("Divide two numbers - valid case")
    void testDivideValid() {
        assertEquals(2, calculator.divide(10, 5));
    }

    @Order(3)
    @Test
    @DisplayName("Divide by zero should throw ArithmeticException")
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> calculator.divide(5, 0));
    }

    @Order(4)
    @Test
    @DisplayName("Factorial of a positive number")
    void testFactorial() {
        assertEquals(120, calculator.factorial(5));
    }

    @Order(5)
    @Test
    @DisplayName("Factorial with negative input throws exception")
    void testFactorialNegative() {
        assertThrows(IllegalArgumentException.class, () -> calculator.factorial(-1));
    }

    @Order(6)
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 3})
    @DisplayName("Parameterized factorial test with timeout")
    void testFactorialParameterized(int input) {
        assertTimeout(Duration.ofMillis(100), () -> calculator.factorial(input));
    }

    @Order(7)
    @Test
    @Timeout(1)
    @DisplayName("Factorial computation should complete under 1 second")
    void testFactorialTimeout() {
        assertEquals(3628800, calculator.factorial(10));
    }

    @Order(8)
    @Test
    @Disabled("This test is intentionally broken for demonstration. To fix: change expected from 5 to 4")
    @DisplayName("Disabled test that fails due to incorrect expected value")
    void disabledTest() {
      //  assertEquals(5, calculator.add(2, 2)); //Wrong answer 
    // Should be 4
    }
}
