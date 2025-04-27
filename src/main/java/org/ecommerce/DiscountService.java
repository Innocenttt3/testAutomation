package org.ecommerce;

import java.util.HashMap;
import java.util.Map;

public class DiscountService {

  private Map<String, Double> promotionalProducts;
  private boolean discountCombinationEnabled;

  private static final double REGULAR_USER_DISCOUNT = 5.0;
  private static final double PREMIUM_USER_DISCOUNT = 15.0;

  public DiscountService() {
    this.promotionalProducts = new HashMap<>();
    this.discountCombinationEnabled = false;
  }

  public void addPromotionalProduct(String productId, double discountPercentage) {
    if (productId == null || productId.trim().isEmpty()) {
      throw new IllegalArgumentException("Product ID cannot be null or empty");
    }
    if (discountPercentage < 0 || discountPercentage > 100) {
      throw new IllegalArgumentException("Discount percentage must be between 0 and 100");
    }

    promotionalProducts.put(productId, discountPercentage);
  }

  public void removePromotionalProduct(String productId) {
    if (productId == null || productId.trim().isEmpty()) {
      throw new IllegalArgumentException("Product ID cannot be null or empty");
    }

    promotionalProducts.remove(productId);
  }

  public void setDiscountCombinationEnabled(boolean enabled) {
    this.discountCombinationEnabled = enabled;
  }

  public boolean isPromotional(String productId) {
    if (productId == null || productId.trim().isEmpty()) {
      throw new IllegalArgumentException("Product ID cannot be null or empty");
    }

    return promotionalProducts.containsKey(productId);
  }

  public double getPromotionalDiscount(String productId) {
    if (productId == null || productId.trim().isEmpty()) {
      throw new IllegalArgumentException("Product ID cannot be null or empty");
    }

    return promotionalProducts.getOrDefault(productId, 0.0);
  }

  public double calculateDiscountedPrice(Product product, User user) {
    if (product == null) {
      throw new IllegalArgumentException("Product cannot be null");
    }
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null");
    }

    double originalPrice = product.getPrice();
    double discountPercentage = 0.0;

    double promotionalDiscount = getPromotionalDiscount(product.getId());

    double userDiscount = 0.0;
    if (user.getType() == User.Type.REGULAR) {
      userDiscount = REGULAR_USER_DISCOUNT;
    } else if (user.getType() == User.Type.PREMIUM) {
      userDiscount = PREMIUM_USER_DISCOUNT;
    }

    if (promotionalDiscount > 0 && !discountCombinationEnabled) {

      discountPercentage = Math.max(promotionalDiscount, userDiscount);
    } else if (promotionalDiscount > 0 && discountCombinationEnabled) {
      double factor1 = 1 - (promotionalDiscount / 100.0);
      double factor2 = 1 - (userDiscount / 100.0);
      return originalPrice * factor1 * factor2;
    } else {
      discountPercentage = userDiscount;
    }
    return originalPrice * (1 - (discountPercentage / 100.0));
  }
}
