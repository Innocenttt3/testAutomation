package org.ecommerce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderHistoryService {
  private Map<String, List<Order>> userOrders;

  public OrderHistoryService() {
    this.userOrders = new HashMap<>();
  }

  public void addOrder(Order order) {
    if (order == null) {
      throw new IllegalArgumentException("Order cannot be null");
    }

    String userId = order.getUser().getId();
    if (!userOrders.containsKey(userId)) {
      userOrders.put(userId, new ArrayList<>());
    }

    userOrders.get(userId).add(order);
  }

  public List<Order> getUserOrders(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null");
    }

    return userOrders.getOrDefault(user.getId(), new ArrayList<>());
  }

  public List<Order> getUserOrdersByStatus(User user, Order.Status status) {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null");
    }
    if (status == null) {
      throw new IllegalArgumentException("Status cannot be null");
    }

    return getUserOrders(user).stream()
        .filter(order -> order.getStatus() == status)
        .collect(Collectors.toList());
  }

  public double getTotalUserSpending(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null");
    }

    return getUserOrders(user).stream().mapToDouble(Order::getTotal).sum();
  }
}
