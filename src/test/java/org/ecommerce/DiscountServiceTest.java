package org.ecommerce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DiscountServiceTest {

  private DiscountService discountService;
  private Product mockProduct;
  private User mockRegularUser;
  private User mockPremiumUser;

  @BeforeEach
  void setUp() {
    discountService = new DiscountService();

    mockProduct = Mockito.mock(Product.class);
    when(mockProduct.getId()).thenReturn("prod1");
    when(mockProduct.getPrice()).thenReturn(100.0);

    mockRegularUser = Mockito.mock(User.class);
    when(mockRegularUser.getId()).thenReturn("user1");
    when(mockRegularUser.getType()).thenReturn(User.Type.REGULAR);

    mockPremiumUser = Mockito.mock(User.class);
    when(mockPremiumUser.getId()).thenReturn("user2");
    when(mockPremiumUser.getType()).thenReturn(User.Type.PREMIUM);
  }

  @Test
  void shouldApplyRegularUserDiscount() {
    // Act
    double discountedPrice = discountService.calculateDiscountedPrice(mockProduct, mockRegularUser);

    // Assert
    assertEquals(95.0, discountedPrice, 0.001);
  }

  @Test
  void shouldApplyPremiumUserDiscount() {
    // Act
    double discountedPrice = discountService.calculateDiscountedPrice(mockProduct, mockPremiumUser);

    // Assert
    assertEquals(85.0, discountedPrice, 0.001);
  }

  @Test
  void shouldApplyPromotionalDiscount() {
    // Arrange
    discountService.addPromotionalProduct(mockProduct.getId(), 25.0);

    // Act
    double discountedPrice = discountService.calculateDiscountedPrice(mockProduct, mockRegularUser);

    // Assert
    assertEquals(75.0, discountedPrice, 0.001);
  }

  @Test
  void shouldCombineDiscountsForPremiumUsers() {
    // Arrange
    discountService.addPromotionalProduct(mockProduct.getId(), 10.0);
    discountService.setDiscountCombinationEnabled(true);

    // Act
    double discountedPrice = discountService.calculateDiscountedPrice(mockProduct, mockPremiumUser);

    // Assert
    assertEquals(76.5, discountedPrice, 0.001);
  }

  @Test
  void shouldThrowExceptionForNullParameters() {
    // Act & Assert
    Exception exception1 =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              discountService.calculateDiscountedPrice(null, mockRegularUser);
            });
    assertEquals("Product cannot be null", exception1.getMessage());

    Exception exception2 =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              discountService.calculateDiscountedPrice(mockProduct, null);
            });
    assertEquals("User cannot be null", exception2.getMessage());
  }
}
