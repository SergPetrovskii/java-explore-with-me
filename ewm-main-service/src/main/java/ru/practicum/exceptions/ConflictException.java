package ru.practicum.exceptions;

public abstract class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}