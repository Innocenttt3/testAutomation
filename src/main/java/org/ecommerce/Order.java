package org.ecommerce;

import java.util.HashMap;
import java.util.Map;

public class Order {
  public enum Status {
    PENDING,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED
  }

  private String id;
  private User user;
  private Map<Product, Integer> items;
  private double total;
  private Status status;

  public Order(String id, ShoppingCart cart) {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalArgumentException("Order ID cannot be null or empty");
    }
    if (cart == null) {
      throw new IllegalArgumentException("Shopping cart cannot be null");
    }
    if (cart.getItems().isEmpty()) {
      throw new IllegalArgumentException("Cannot create order from empty cart");
    }

    this.id = id;
    this.user = cart.getUser();
    this.items = new HashMap<>(cart.getItems());
    this.total = cart.calculateTotal();
    this.status = Status.PENDING;

    cart.clear();
  }

  public String getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public Map<Product, Integer> getItems() {
    return items;
  }

  public double getTotal() {
    return total;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    if (status == null) {
      throw new IllegalArgumentException("Status cannot be null");
    }
    this.status = status;
  }
}
