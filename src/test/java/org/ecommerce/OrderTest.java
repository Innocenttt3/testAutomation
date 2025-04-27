package org.ecommerce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderTest {

  private User mockUser;
  private ShoppingCart mockCart;
  private Product mockProduct1;
  private Product mockProduct2;
  private Map<Product, Integer> cartItems;

  @BeforeEach
  void setUp() {
    mockUser = Mockito.mock(User.class);
    mockCart = Mockito.mock(ShoppingCart.class);
    mockProduct1 = Mockito.mock(Product.class);
    mockProduct2 = Mockito.mock(Product.class);

    cartItems = new HashMap<>();
    cartItems.put(mockProduct1, 2);
    cartItems.put(mockProduct2, 1);

    when(mockUser.getId()).thenReturn("user123");
    when(mockCart.getUser()).thenReturn(mockUser);
    when(mockCart.getItems()).thenReturn(cartItems);
    when(mockCart.calculateTotal()).thenReturn(400.0);
  }

  @Test
  void shouldCreateOrderFromShoppingCart() {
    // Act
    Order order = new Order("order123", mockCart);

    // Assert
    assertEquals("order123", order.getId());
    assertEquals(mockUser, order.getUser());
    assertEquals(cartItems, order.getItems());
    assertEquals(400.0, order.getTotal(), 0.001);
    assertEquals(Order.Status.PENDING, order.getStatus());

    // Verify
    verify(mockCart).clear();
  }

  @Test
  void shouldUpdateOrderStatus() {
    // Arrange
    Order order = new Order("order123", mockCart);

    // Act
    order.setStatus(Order.Status.SHIPPED);

    // Assert
    assertEquals(Order.Status.SHIPPED, order.getStatus());
  }

  @Test
  void shouldThrowExceptionWhenIdIsNull() {
    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              new Order(null, mockCart);
            });

    assertEquals("Order ID cannot be null or empty", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenIdIsEmpty() {
    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              new Order("", mockCart);
            });

    assertEquals("Order ID cannot be null or empty", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenCartIsNull() {
    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              new Order("order123", null);
            });

    assertEquals("Shopping cart cannot be null", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenCartIsEmpty() {
    // Arrange
    ShoppingCart emptyCart = Mockito.mock(ShoppingCart.class);
    when(emptyCart.getItems()).thenReturn(new HashMap<>());

    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              new Order("order123", emptyCart);
            });

    assertEquals("Cannot create order from empty cart", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenSettingNullStatus() {
    // Arrange
    Order order = new Order("order123", mockCart);

    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              order.setStatus(null);
            });

    assertEquals("Status cannot be null", exception.getMessage());
  }
}
