package com.example.bookmyshow_be.Exceptions;

public class PasswordIncorrectException extends RuntimeException{
    public PasswordIncorrectException(String message) {
        super(message);
    }
}
