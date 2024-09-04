package org.bakushkin.springregioncatalog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Objects;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ErrorResponse(
                e.getMessage(),
                "Incorrect request. " +
                        "Field: \"" + Objects.requireNonNull(e.getFieldError()).getField() + "\" " +
                        "Reason: \"" + Objects.requireNonNull(e.getFieldError()).getDefaultMessage() + "\"",
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now().toString());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        return new ErrorResponse(
                e.getMessage(),
                "The required object was not found.",
                HttpStatus.NOT_FOUND.toString(),
                LocalDateTime.now().toString());
    }
}
