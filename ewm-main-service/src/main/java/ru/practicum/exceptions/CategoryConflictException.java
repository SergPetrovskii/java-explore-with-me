package ru.practicum.exceptions;

public class CategoryConflictException extends ConflictException {
    public CategoryConflictException(String message) {
        super(message);
    }
}