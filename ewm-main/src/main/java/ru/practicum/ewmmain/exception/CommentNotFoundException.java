package ru.practicum.ewmmain.exception;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(Long id) {
        super(String.format("Comment с id=%d не найдена!", id));
    }
}
