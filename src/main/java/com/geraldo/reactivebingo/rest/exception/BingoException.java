package com.geraldo.reactivebingo.rest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public abstract class BingoException extends RuntimeException {
    private final String message;
    private final HttpStatus status;
}
