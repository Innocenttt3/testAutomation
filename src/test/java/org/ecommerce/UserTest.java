package org.ecommerce;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

  @Test
  void shouldCreateUserWithCorrectAttributes() {
    // Arrange
    String id = "user123";
    String username = "john_doe";
    String email = "john@example.com";

    // Act
    User user = new User(id, username, email);

    // Assert
    assertEquals(id, user.getId());
    assertEquals(username, user.getUsername());
    assertEquals(email, user.getEmail());
  }

  @Test
  void shouldUpdateUserEmail() {
    // Arrange
    User user = new User("user123", "john_doe", "john@example.com");
    String newEmail = "john.doe@example.com";

    // Act
    user.setEmail(newEmail);

    // Assert
    assertEquals(newEmail, user.getEmail());
  }

  @Test
  void shouldThrowExceptionWhenIdIsNull() {
    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              new User(null, "john_doe", "john@example.com");
            });

    assertEquals("User ID cannot be null or empty", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenIdIsEmpty() {
    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              new User("", "john_doe", "john@example.com");
            });

    assertEquals("User ID cannot be null or empty", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenUsernameIsNull() {
    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              new User("user123", null, "john@example.com");
            });

    assertEquals("Username cannot be null or empty", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenEmailIsInvalid() {
    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              new User("user123", "john_doe", "invalid-email");
            });

    assertEquals("Email must be valid and contain @", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenUpdatingToInvalidEmail() {
    // Arrange
    User user = new User("user123", "john_doe", "john@example.com");

    // Act & Assert
    Exception exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              user.setEmail("invalid-email");
            });

    assertEquals("Email must be valid and contain @", exception.getMessage());
  }
}
