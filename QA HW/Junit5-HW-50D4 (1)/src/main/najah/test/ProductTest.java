package main.najah.test;

import main.najah.code.Product;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    @DisplayName("Should create product with valid name and price")
    void testValidProductCreation() {
        Product product = new Product("Laptop", 1000);
        assertAll(
            () -> assertEquals("Laptop", product.getName()),
            () -> assertEquals(1000, product.getPrice()),
            () -> assertEquals(0, product.getDiscount()),
            () -> assertEquals(1000, product.getFinalPrice())
        );
    }

    @Test
    @DisplayName("Should throw exception for negative price")
    void testNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> new Product("Phone", -100));
    }

    @Test
    @DisplayName("Should apply valid discount and return correct final price")
    void testApplyValidDiscount() {
        Product p = new Product("TV", 2000);
        p.applyDiscount(20);
        assertAll(
            () -> assertEquals(20, p.getDiscount()),
            () -> assertEquals(1600, p.getFinalPrice())
        );
    }

    @Test
    @DisplayName("Should throw exception for invalid discount percentage")
    void testInvalidDiscount() {
        Product p = new Product("Monitor", 500);
        assertThrows(IllegalArgumentException.class, () -> p.applyDiscount(80));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, 10, 50})
    @DisplayName("Should apply boundary discounts correctly")
    void testBoundaryDiscounts(double discount) {
        Product p = new Product("Chair", 100);
        p.applyDiscount(discount);
        assertTrue(p.getFinalPrice() <= 100);
    }

    @ParameterizedTest
    @CsvSource({
        "Shoes, 150, 10, 135.0",
        "Book, 50, 0, 50.0",
        "Watch, 200, 50, 100.0"
    })
    @DisplayName("Should calculate final price for various products using discount")
    void testFinalPriceCalculation(String name, double price, double discount, double expectedFinalPrice) {
        Product product = new Product(name, price);
        product.applyDiscount(discount);
        assertEquals(expectedFinalPrice, product.getFinalPrice(), 0.001);
    }

    @Test
    @Timeout(1)
    @DisplayName("Product creation and discount application should complete within 1 second")
    void testPerformanceOfDiscountApplication() {
        Product product = new Product("Tablet", 300);
        product.applyDiscount(25);
        assertEquals(225, product.getFinalPrice());
    }

    @Test
    @Disabled("This test is intentionally broken for demonstration. To fix: change expected from 5 to 4")
    @DisplayName("Disabled test with wrong expectation")
    void disabledTest() {
        // assertEquals(5, calculator.add(2, 2)); 
      //  System.out.println("Wrong answer");
        // Should be 4
    }
}