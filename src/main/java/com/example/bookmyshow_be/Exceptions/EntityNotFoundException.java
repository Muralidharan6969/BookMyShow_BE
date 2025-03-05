package com.example.bookmyshow_be.Exceptions;

    public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String message) {
        super(message);
    }
}
