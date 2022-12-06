package ru.practicum.ewmmain.exception;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(Long id) {
        super(String.format("Event with id=%d not found", id));
    }
}
