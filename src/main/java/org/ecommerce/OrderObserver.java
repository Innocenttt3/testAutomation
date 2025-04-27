package org.ecommerce;

public interface OrderObserver {

  void onOrderStatusChange(Order order, Order.Status newStatus);
}
