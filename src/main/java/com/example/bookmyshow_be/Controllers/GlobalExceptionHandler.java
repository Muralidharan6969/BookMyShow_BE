package com.example.bookmyshow_be.Controllers;

import com.example.bookmyshow_be.DTOs.ResponseWrapper;
import com.example.bookmyshow_be.Exceptions.DuplicateEmailException;
import com.example.bookmyshow_be.Exceptions.EntityNotFoundException;
import com.example.bookmyshow_be.Exceptions.PasswordIncorrectException;
import com.example.bookmyshow_be.Exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseWrapper(exception.getMessage(), null));
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<?> handleDuplicateEmail(DuplicateEmailException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ResponseWrapper(exception.getMessage(), null));
    }

    @ExceptionHandler(PasswordIncorrectException.class)
    public ResponseEntity<?> handleIncorrectPassword(PasswordIncorrectException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseWrapper(exception.getMessage(), null));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseWrapper(exception.getMessage(), null));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseWrapper(exception.getMessage(), null));
    }
}
