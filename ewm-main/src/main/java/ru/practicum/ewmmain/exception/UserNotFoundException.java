package ru.practicum.ewmmain.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super(String.format("User с id=%d не найдена!", id));
    }
}
