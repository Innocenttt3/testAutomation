package org.ecommerce;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
  private User user;
  private Map<Product, Integer> items;

  public ShoppingCart(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null");
    }

    this.user = user;
    this.items = new HashMap<>();
  }

  public User getUser() {
    return user;
  }

  public Map<Product, Integer> getItems() {
    return items;
  }

  public void addProduct(Product product, int quantity) {
    if (product == null) {
      throw new IllegalArgumentException("Product cannot be null");
    }
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be positive");
    }

    items.put(product, items.getOrDefault(product, 0) + quantity);
  }

  public void removeProduct(Product product) {
    if (product == null) {
      throw new IllegalArgumentException("Product cannot be null");
    }
    if (!items.containsKey(product)) {
      throw new IllegalArgumentException("Product not found in cart");
    }

    items.remove(product);
  }

  public double calculateTotal() {
    return items.entrySet().stream()
        .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
        .sum();
  }

  public void clear() {
    items.clear();
  }
}
