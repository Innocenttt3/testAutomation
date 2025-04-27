package org.ecommerce;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTypeTest {

  @Test
  void shouldCreateUserWithSpecifiedType() {
    // Arrange
    String id = "user123";
    String username = "john_doe";
    String email = "john@example.com";
    User.Type type = User.Type.PREMIUM;

    // Act
    User user = new User(id, username, email, type);

    // Assert
    assertEquals(id, user.getId());
    assertEquals(username, user.getUsername());
    assertEquals(email, user.getEmail());
    assertEquals(type, user.getType());
  }

  @Test
  void shouldCreateRegularUserByDefault() {
    // Act
    User user = new User("user123", "john_doe", "john@example.com");

    // Assert
    assertEquals(User.Type.REGULAR, user.getType());
  }

  @Test
  void shouldUpdateUserType() {
    // Arrange
    User user = new User("user123", "john_doe", "john@example.com");

    // Act
    user.setType(User.Type.PREMIUM);

    // Assert
    assertEquals(User.Type.PREMIUM, user.getType());
  }

  @Test
  void shouldThrowExceptionWhenTypeIsNull() {
    // Arrange
    User user = new User("user123", "john_doe", "john@example.com");

    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              user.setType(null);
            });

    assertEquals("User type cannot be null", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenCreatingUserWithNullType() {
    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              new User("user123", "john_doe", "john@example.com", null);
            });

    assertEquals("User type cannot be null", exception.getMessage());
  }
}
