package ru.practicum.exceptions;

public class ValidationException extends IllegalArgumentException {

  public ValidationException(String message) {
    super(message);
  }

}
