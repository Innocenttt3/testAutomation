package org.ecommerce;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

  @Test
  void shouldCreateProductWithCorrectAttributes() {
    // Arrange
    String id = "prod123";
    String name = "Smartphone";
    double price = 599.99;

    // Act
    Product product = new Product(id, name, price);

    // Assert
    assertEquals(id, product.getId());
    assertEquals(name, product.getName());
    assertEquals(price, product.getPrice(), 0.001);
  }

  @Test
  void shouldUpdateProductPrice() {
    // Arrange
    Product product = new Product("prod123", "Smartphone", 599.99);
    double newPrice = 499.99;

    // Act
    product.setPrice(newPrice);

    // Assert
    assertEquals(newPrice, product.getPrice(), 0.001);
  }

  @Test
  void shouldThrowExceptionWhenIdIsNull() {
    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              new Product(null, "Smartphone", 599.99);
            });

    assertEquals("Product ID cannot be null or empty", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenNameIsEmpty() {
    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              new Product("prod123", "", 599.99);
            });

    assertEquals("Product name cannot be null or empty", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenPriceIsNegative() {
    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              new Product("prod123", "Smartphone", -10.0);
            });

    assertEquals("Product price cannot be negative", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenSettingNegativePrice() {
    // Arrange
    Product product = new Product("prod123", "Smartphone", 599.99);

    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              product.setPrice(-10.0);
            });

    assertEquals("Product price cannot be negative", exception.getMessage());
  }
}
