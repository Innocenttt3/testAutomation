package org.ecommerce;

public class User {
  public enum Type {
    REGULAR,
    PREMIUM
  }

  private String id;
  private String username;
  private String email;
  private Type type;

  public User(String id, String username, String email) {
    this(id, username, email, Type.REGULAR);
  }

  public User(String id, String username, String email, Type type) {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalArgumentException("User ID cannot be null or empty");
    }
    if (username == null || username.trim().isEmpty()) {
      throw new IllegalArgumentException("Username cannot be null or empty");
    }
    if (email == null || !email.contains("@")) {
      throw new IllegalArgumentException("Email must be valid and contain @");
    }
    if (type == null) {
      throw new IllegalArgumentException("User type cannot be null");
    }

    this.id = id;
    this.username = username;
    this.email = email;
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public Type getType() {
    return type;
  }

  public void setEmail(String email) {
    if (email == null || !email.contains("@")) {
      throw new IllegalArgumentException("Email must be valid and contain @");
    }
    this.email = email;
  }

  public void setType(Type type) {
    if (type == null) {
      throw new IllegalArgumentException("User type cannot be null");
    }
    this.type = type;
  }
}
