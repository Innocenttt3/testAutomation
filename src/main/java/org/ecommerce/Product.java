package org.ecommerce;

public class Product {
  private String id;
  private String name;
  private double price;

  public Product(String id, String name, double price) {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalArgumentException("Product ID cannot be null or empty");
    }
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Product name cannot be null or empty");
    }
    if (price < 0) {
      throw new IllegalArgumentException("Product price cannot be negative");
    }

    this.id = id;
    this.name = name;
    this.price = price;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    if (price < 0) {
      throw new IllegalArgumentException("Product price cannot be negative");
    }
    this.price = price;
  }
}
