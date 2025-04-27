package org.ecommerce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InventoryServiceTest {

  private InventoryService inventoryService;
  private Product mockProduct1;
  private Product mockProduct2;

  @BeforeEach
  void setUp() {
    inventoryService = new InventoryService();
    mockProduct1 = Mockito.mock(Product.class);
    mockProduct2 = Mockito.mock(Product.class);

    when(mockProduct1.getId()).thenReturn("prod1");
    when(mockProduct2.getId()).thenReturn("prod2");
  }

  @Test
  void shouldAddProductToInventory() {
    // Act
    inventoryService.addProduct(mockProduct1, 10);

    // Assert
    assertTrue(inventoryService.isInStock(mockProduct1));
    assertEquals(10, inventoryService.getQuantity(mockProduct1));
  }

  @Test
  void shouldUpdateQuantityWhenAddingSameProduct() {
    // Arrange
    inventoryService.addProduct(mockProduct1, 10);

    // Act
    inventoryService.addProduct(mockProduct1, 5);

    // Assert
    assertEquals(15, inventoryService.getQuantity(mockProduct1));
  }

  @Test
  void shouldRemoveProductFromInventory() {
    // Arrange
    inventoryService.addProduct(mockProduct1, 10);
    inventoryService.addProduct(mockProduct2, 5);

    // Act
    inventoryService.removeProduct(mockProduct1);

    // Assert
    assertFalse(inventoryService.isInStock(mockProduct1));
    assertTrue(inventoryService.isInStock(mockProduct2));
  }

  @Test
  void shouldReturnFalseWhenProductIsNotInStock() {
    // Arrange
    inventoryService.addProduct(mockProduct1, 0);

    // Act & Assert
    assertFalse(inventoryService.isInStock(mockProduct1));
  }

  @Test
  void shouldProcessOrderSuccessfullyWhenAllProductsInStock() {
    // Arrange
    inventoryService.addProduct(mockProduct1, 10);
    inventoryService.addProduct(mockProduct2, 5);

    Order mockOrder = Mockito.mock(Order.class);
    Map<Product, Integer> orderItems = new HashMap<>();
    orderItems.put(mockProduct1, 5);
    orderItems.put(mockProduct2, 3);

    when(mockOrder.getItems()).thenReturn(orderItems);

    // Act
    boolean result = inventoryService.processOrder(mockOrder);

    // Assert
    assertTrue(result);
    assertEquals(5, inventoryService.getQuantity(mockProduct1));
    assertEquals(2, inventoryService.getQuantity(mockProduct2));
    verify(mockOrder).setStatus(Order.Status.PROCESSING);
  }

  @Test
  void shouldNotProcessOrderWhenProductsNotInStock() {
    // Arrange
    inventoryService.addProduct(mockProduct1, 3);
    inventoryService.addProduct(mockProduct2, 5);

    Order mockOrder = Mockito.mock(Order.class);
    Map<Product, Integer> orderItems = new HashMap<>();
    orderItems.put(mockProduct1, 5);
    orderItems.put(mockProduct2, 3);

    when(mockOrder.getItems()).thenReturn(orderItems);

    // Act
    boolean result = inventoryService.processOrder(mockOrder);

    // Assert
    assertFalse(result);
    assertEquals(3, inventoryService.getQuantity(mockProduct1));
    assertEquals(5, inventoryService.getQuantity(mockProduct2));
    verify(mockOrder, never()).setStatus(any(Order.Status.class));
  }

  @Test
  void shouldThrowExceptionWhenAddingNullProduct() {
    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              inventoryService.addProduct(null, 10);
            });

    assertEquals("Product cannot be null", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenAddingNegativeQuantity() {
    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              inventoryService.addProduct(mockProduct1, -1);
            });

    assertEquals("Quantity cannot be negative", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenRemovingNullProduct() {
    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              inventoryService.removeProduct(null);
            });

    assertEquals("Product cannot be null", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenRemovingProductNotInInventory() {
    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              inventoryService.removeProduct(mockProduct1);
            });

    assertEquals("Product not found in inventory", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenCheckingNullProductInStock() {
    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              inventoryService.isInStock(null);
            });

    assertEquals("Product cannot be null", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenGettingQuantityOfNullProduct() {
    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              inventoryService.getQuantity(null);
            });

    assertEquals("Product cannot be null", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenProcessingNullOrder() {
    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              inventoryService.processOrder(null);
            });

    assertEquals("Order cannot be null", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenProcessingEmptyOrder() {
    // Arrange
    Order mockOrder = Mockito.mock(Order.class);
    Map<Product, Integer> emptyOrderItems = new HashMap<>();

    when(mockOrder.getItems()).thenReturn(emptyOrderItems);

    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              inventoryService.processOrder(mockOrder);
            });

    assertEquals("Order must contain at least one item", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenOrderContainsNullProduct() {
    // Arrange
    inventoryService.addProduct(mockProduct1, 10);

    Order mockOrder = Mockito.mock(Order.class);
    Map<Product, Integer> orderItems = new HashMap<>();
    orderItems.put(null, 5);

    when(mockOrder.getItems()).thenReturn(orderItems);

    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              inventoryService.processOrder(mockOrder);
            });

    assertEquals("Order contains null product", exception.getMessage());
  }
}
