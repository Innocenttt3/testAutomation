package org.ecommerce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

public class NotificationServiceTest {

  private NotificationService notificationService;
  private OrderObserver mockEmailNotifier;
  private OrderObserver mockSmsNotifier;
  private Order mockOrder;
  private User mockUser;

  @BeforeEach
  void setUp() {
    notificationService = new NotificationService();

    mockEmailNotifier = Mockito.mock(OrderObserver.class);
    mockSmsNotifier = Mockito.mock(OrderObserver.class);

    mockUser = Mockito.mock(User.class);
    when(mockUser.getId()).thenReturn("user123");
    when(mockUser.getEmail()).thenReturn("user@example.com");

    mockOrder = Mockito.mock(Order.class);
    when(mockOrder.getId()).thenReturn("order123");
    when(mockOrder.getUser()).thenReturn(mockUser);
    when(mockOrder.getStatus()).thenReturn(Order.Status.PENDING);
  }

  @Test
  void shouldNotifyAllObserversOnOrderStatusChange() {
    // Arrange
    notificationService.addObserver(mockEmailNotifier);
    notificationService.addObserver(mockSmsNotifier);

    // Act
    notificationService.notifyOrderStatusChange(mockOrder, Order.Status.SHIPPED);

    // Assert
    verify(mockEmailNotifier).onOrderStatusChange(mockOrder, Order.Status.SHIPPED);
    verify(mockSmsNotifier).onOrderStatusChange(mockOrder, Order.Status.SHIPPED);
  }

  @Test
  void shouldRemoveObserver() {
    // Arrange
    notificationService.addObserver(mockEmailNotifier);
    notificationService.addObserver(mockSmsNotifier);

    // Act
    notificationService.removeObserver(mockSmsNotifier);
    notificationService.notifyOrderStatusChange(mockOrder, Order.Status.SHIPPED);

    // Assert
    verify(mockEmailNotifier).onOrderStatusChange(mockOrder, Order.Status.SHIPPED);
    verify(mockSmsNotifier, never()).onOrderStatusChange(any(Order.class), any(Order.Status.class));
  }

  @Test
  void shouldNotCallRemovedObserver() {
    // Arrange
    notificationService.addObserver(mockEmailNotifier);

    // Act
    notificationService.notifyOrderStatusChange(mockOrder, Order.Status.PROCESSING);

    // Verify
    verify(mockEmailNotifier).onOrderStatusChange(mockOrder, Order.Status.PROCESSING);

    // Remove
    notificationService.removeObserver(mockEmailNotifier);
    notificationService.notifyOrderStatusChange(mockOrder, Order.Status.SHIPPED);

    // Verify
    verify(mockEmailNotifier, times(1))
        .onOrderStatusChange(any(Order.class), any(Order.Status.class));
  }

  @Test
  void shouldHandleMultipleNotificationsForSameOrder() {
    // Arrange
    notificationService.addObserver(mockEmailNotifier);

    // Act
    notificationService.notifyOrderStatusChange(mockOrder, Order.Status.PROCESSING);
    notificationService.notifyOrderStatusChange(mockOrder, Order.Status.SHIPPED);
    notificationService.notifyOrderStatusChange(mockOrder, Order.Status.DELIVERED);

    // Assert
    verify(mockEmailNotifier).onOrderStatusChange(mockOrder, Order.Status.PROCESSING);
    verify(mockEmailNotifier).onOrderStatusChange(mockOrder, Order.Status.SHIPPED);
    verify(mockEmailNotifier).onOrderStatusChange(mockOrder, Order.Status.DELIVERED);
  }
}
