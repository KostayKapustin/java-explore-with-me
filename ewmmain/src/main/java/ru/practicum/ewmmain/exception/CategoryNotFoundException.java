package ru.practicum.ewmmain.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long id) {
        super(String.format("Category with id=%d not found", id));
    }
}
