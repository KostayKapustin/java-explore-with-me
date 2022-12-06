package ru.practicum.ewmmain.exception;

public class CompilationNotFoundException extends RuntimeException {

    public CompilationNotFoundException(Long id) {
        super(String.format("Compilation с id=%d не найдена!", id));
    }
}
