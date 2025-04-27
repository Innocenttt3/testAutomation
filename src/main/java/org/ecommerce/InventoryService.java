package org.ecommerce;

import java.util.HashMap;
import java.util.Map;

public class InventoryService {
  private Map<String, Integer> inventory;
  private Map<String, Product> products;

  public InventoryService() {
    inventory = new HashMap<>();
    products = new HashMap<>();
  }

  public void addProduct(Product product, int quantity) {
    if (product == null) {
      throw new IllegalArgumentException("Product cannot be null");
    }
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity cannot be negative");
    }

    String productId = product.getId();
    products.put(productId, product);
    inventory.put(productId, inventory.getOrDefault(productId, 0) + quantity);
  }

  public void removeProduct(Product product) {
    if (product == null) {
      throw new IllegalArgumentException("Product cannot be null");
    }
    if (!products.containsKey(product.getId())) {
      throw new IllegalArgumentException("Product not found in inventory");
    }

    String productId = product.getId();
    inventory.remove(productId);
    products.remove(productId);
  }

  public boolean isInStock(Product product) {
    if (product == null) {
      throw new IllegalArgumentException("Product cannot be null");
    }

    Integer quantity = inventory.get(product.getId());
    return quantity != null && quantity > 0;
  }

  public int getQuantity(Product product) {
    if (product == null) {
      throw new IllegalArgumentException("Product cannot be null");
    }

    return inventory.getOrDefault(product.getId(), 0);
  }

  public boolean processOrder(Order order) {
    if (order == null) {
      throw new IllegalArgumentException("Order cannot be null");
    }
    if (order.getItems().isEmpty()) {
      throw new IllegalArgumentException("Order must contain at least one item");
    }

    for (Map.Entry<Product, Integer> entry : order.getItems().entrySet()) {
      Product product = entry.getKey();
      int quantity = entry.getValue();

      if (product == null) {
        throw new IllegalArgumentException("Order contains null product");
      }

      if (getQuantity(product) < quantity) {
        return false;
      }
    }

    for (Map.Entry<Product, Integer> entry : order.getItems().entrySet()) {
      Product product = entry.getKey();
      int quantity = entry.getValue();

      inventory.put(product.getId(), getQuantity(product) - quantity);
    }
    order.setStatus(Order.Status.PROCESSING);

    return true;
  }
}
