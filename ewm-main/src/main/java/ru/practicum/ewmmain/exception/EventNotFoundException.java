package ru.practicum.ewmmain.exception;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(Long id) {
        super(String.format("Event с id=%d не найдена!", id));
    }
}
