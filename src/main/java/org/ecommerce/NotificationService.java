package org.ecommerce;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {
  private List<OrderObserver> observers;

  public NotificationService() {
    this.observers = new ArrayList<>();
  }

  public void addObserver(OrderObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException("Observer cannot be null");
    }

    if (!observers.contains(observer)) {
      observers.add(observer);
    }
  }

  public void removeObserver(OrderObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException("Observer cannot be null");
    }

    observers.remove(observer);
  }

  public void notifyOrderStatusChange(Order order, Order.Status newStatus) {
    if (order == null) {
      throw new IllegalArgumentException("Order cannot be null");
    }
    if (newStatus == null) {
      throw new IllegalArgumentException("New status cannot be null");
    }

    for (OrderObserver observer : observers) {
      observer.onOrderStatusChange(order, newStatus);
    }
  }
}
