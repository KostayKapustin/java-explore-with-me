package ru.practicum.ewmmain.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long id) {
        super(String.format("Category с id=%d не найдена!", id));
    }
}
