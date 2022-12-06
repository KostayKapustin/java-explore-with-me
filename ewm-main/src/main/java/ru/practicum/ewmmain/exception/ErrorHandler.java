package ru.practicum.ewmmain.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler({CategoryNotFoundException.class, CompilationNotFoundException.class,
            EventNotFoundException.class, RequestNotFoundExceprion.class, UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final RuntimeException e) {
        log.info(HttpStatus.BAD_REQUEST + ": " + e.getMessage());
        return new ErrorResponse(e.getClass().toString(), e.getMessage(), "Элемент не найден",
                HttpStatus.NOT_FOUND.toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConstraintViolationException(final ConstraintViolationException e) {
        log.info(HttpStatus.CONFLICT + ": " + e.getMessage());
        return new ErrorResponse(e.getConstraintName(), e.getMessage(), "Исключение ограничения",
                HttpStatus.CONFLICT.toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingParameterException(final MissingServletRequestParameterException e) {
        log.info(HttpStatus.BAD_REQUEST + ": " + e.getMessage());
        return new ErrorResponse(e.getParameterName() + " параметр отсутствует", e.getMessage(),
                "Требуемый параметр не найден", HttpStatus.BAD_REQUEST.toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.info(HttpStatus.BAD_REQUEST + ": " + e.getMessage());
        List<String> errors = new ArrayList<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : e.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        return new ErrorResponse(errors, e.getFieldErrors().toString(), "Недопустимый параметр",
                HttpStatus.BAD_REQUEST.toString());
    }
}
