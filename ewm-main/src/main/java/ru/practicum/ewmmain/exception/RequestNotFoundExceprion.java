package ru.practicum.ewmmain.exception;

public class RequestNotFoundExceprion extends RuntimeException {

    public RequestNotFoundExceprion(Long id) {
        super(String.format("Request with id=%d not found", id));
    }
}
