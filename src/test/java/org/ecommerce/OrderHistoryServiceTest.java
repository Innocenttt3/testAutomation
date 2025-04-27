package org.ecommerce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderHistoryServiceTest {

  private OrderHistoryService orderHistoryService;
  private User mockUser;
  private Order mockOrder1;
  private Order mockOrder2;

  @BeforeEach
  void setUp() {
    orderHistoryService = new OrderHistoryService();

    mockUser = Mockito.mock(User.class);
    when(mockUser.getId()).thenReturn("user123");

    mockOrder1 = Mockito.mock(Order.class);
    when(mockOrder1.getId()).thenReturn("order1");
    when(mockOrder1.getUser()).thenReturn(mockUser);
    when(mockOrder1.getTotal()).thenReturn(150.0);
    when(mockOrder1.getStatus()).thenReturn(Order.Status.DELIVERED);

    mockOrder2 = Mockito.mock(Order.class);
    when(mockOrder2.getId()).thenReturn("order2");
    when(mockOrder2.getUser()).thenReturn(mockUser);
    when(mockOrder2.getTotal()).thenReturn(250.0);
    when(mockOrder2.getStatus()).thenReturn(Order.Status.SHIPPED);
  }

  @Test
  void shouldAddOrderToHistory() {
    // Act
    orderHistoryService.addOrder(mockOrder1);

    // Assert
    List<Order> userOrders = orderHistoryService.getUserOrders(mockUser);
    assertEquals(1, userOrders.size());
    assertTrue(userOrders.contains(mockOrder1));
  }

  @Test
  void shouldGetMultipleUserOrders() {
    // Arrange
    orderHistoryService.addOrder(mockOrder1);
    orderHistoryService.addOrder(mockOrder2);

    // Act
    List<Order> userOrders = orderHistoryService.getUserOrders(mockUser);

    // Assert
    assertEquals(2, userOrders.size());
    assertTrue(userOrders.contains(mockOrder1));
    assertTrue(userOrders.contains(mockOrder2));
  }

  @Test
  void shouldGetOrdersByStatus() {
    // Arrange
    orderHistoryService.addOrder(mockOrder1);
    orderHistoryService.addOrder(mockOrder2);

    // Act
    List<Order> deliveredOrders =
        orderHistoryService.getUserOrdersByStatus(mockUser, Order.Status.DELIVERED);

    // Assert
    assertEquals(1, deliveredOrders.size());
    assertTrue(deliveredOrders.contains(mockOrder1));
  }

  @Test
  void shouldCalculateTotalSpending() {
    // Arrange
    orderHistoryService.addOrder(mockOrder1);
    orderHistoryService.addOrder(mockOrder2);

    // Act
    double totalSpent = orderHistoryService.getTotalUserSpending(mockUser);

    // Assert
    assertEquals(400.0, totalSpent, 0.001);
  }

  @Test
  void shouldReturnEmptyListForNewUser() {
    // Arrange
    User newUser = Mockito.mock(User.class);
    when(newUser.getId()).thenReturn("newUser");

    // Act
    List<Order> orders = orderHistoryService.getUserOrders(newUser);

    // Assert
    assertTrue(orders.isEmpty());
  }

  @Test
  void shouldThrowExceptionWhenUserIsNull() {
    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              orderHistoryService.getUserOrders(null);
            });

    assertEquals("User cannot be null", exception.getMessage());
  }
}
