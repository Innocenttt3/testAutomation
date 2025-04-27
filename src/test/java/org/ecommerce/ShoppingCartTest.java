package org.ecommerce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ShoppingCartTest {

  private User mockUser;
  private Product mockProduct1;
  private Product mockProduct2;
  private ShoppingCart cart;

  @BeforeEach
  void setUp() {
    mockUser = Mockito.mock(User.class);
    mockProduct1 = Mockito.mock(Product.class);
    mockProduct2 = Mockito.mock(Product.class);

    when(mockUser.getId()).thenReturn("user123");
    when(mockProduct1.getId()).thenReturn("prod1");
    when(mockProduct1.getPrice()).thenReturn(100.0);
    when(mockProduct2.getId()).thenReturn("prod2");
    when(mockProduct2.getPrice()).thenReturn(200.0);

    cart = new ShoppingCart(mockUser);
  }

  @Test
  void shouldAddProductToCart() {
    // Act
    cart.addProduct(mockProduct1, 2);

    // Assert
    Map<Product, Integer> items = cart.getItems();
    assertEquals(1, items.size());
    assertTrue(items.containsKey(mockProduct1));
    assertEquals(2, items.get(mockProduct1));
  }

  @Test
  void shouldUpdateQuantityWhenAddingSameProduct() {
    // Arrange
    cart.addProduct(mockProduct1, 2);

    // Act
    cart.addProduct(mockProduct1, 3);

    // Assert
    Map<Product, Integer> items = cart.getItems();
    assertEquals(1, items.size());
    assertEquals(5, items.get(mockProduct1));
  }

  @Test
  void shouldRemoveProductFromCart() {
    // Arrange
    cart.addProduct(mockProduct1, 2);
    cart.addProduct(mockProduct2, 1);

    // Act
    cart.removeProduct(mockProduct1);

    // Assert
    Map<Product, Integer> items = cart.getItems();
    assertEquals(1, items.size());
    assertFalse(items.containsKey(mockProduct1));
    assertTrue(items.containsKey(mockProduct2));
  }

  @Test
  void shouldCalculateTotalCorrectly() {
    // Arrange
    cart.addProduct(mockProduct1, 2);
    cart.addProduct(mockProduct2, 1);

    // Act
    double total = cart.calculateTotal();

    // Assert
    assertEquals(400.0, total, 0.001);
  }

  @Test
  void shouldClearCart() {
    // Arrange
    cart.addProduct(mockProduct1, 2);
    cart.addProduct(mockProduct2, 1);

    // Act
    cart.clear();

    // Assert
    assertTrue(cart.getItems().isEmpty());
  }

  @Test
  void shouldThrowExceptionWhenUserIsNull() {
    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              new ShoppingCart(null);
            });

    assertEquals("User cannot be null", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenAddingNullProduct() {
    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              cart.addProduct(null, 1);
            });

    assertEquals("Product cannot be null", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenAddingZeroOrNegativeQuantity() {
    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              cart.addProduct(mockProduct1, 0);
            });

    assertEquals("Quantity must be positive", exception.getMessage());

    Exception exception2 =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              cart.addProduct(mockProduct1, -1);
            });

    assertEquals("Quantity must be positive", exception2.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenRemovingNullProduct() {
    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              cart.removeProduct(null);
            });

    assertEquals("Product cannot be null", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenRemovingProductNotInCart() {
    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              cart.removeProduct(mockProduct1);
            });

    assertEquals("Product not found in cart", exception.getMessage());
  }
}
