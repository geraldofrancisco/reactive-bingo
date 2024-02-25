package com.geraldo.reactivebingo.rest.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundException extends BingoException{
    public NotFoundException(String message) {
        super(message, NOT_FOUND);
    }
}
