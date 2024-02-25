package com.geraldo.reactivebingo.rest.exception;


import com.geraldo.reactivebingo.domain.model.exception.ErrorFieldResponse;
import com.geraldo.reactivebingo.domain.model.exception.ExceptionResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Locale;

import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.GENERIC_ERROR;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class ReactiveBingoExceptionHandler {
    private MessageSource messageSource;


    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleException (final Exception ex) {
        return Mono.just(getMessage(GENERIC_ERROR))
                .flatMap(message -> getExceptionResponse(INTERNAL_SERVER_ERROR, message, null))
                .doFirst(() -> log.error("There was a generic error: ", ex));
    }

    @ExceptionHandler(BingoException.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleException(final BingoException ex) {
        return Mono.just(getMessage(ex.getMessage()))
                .flatMap(message -> getExceptionResponse(ex.getStatus(), message, null))
                .doFirst(() -> log.error("There was a Bingo error: ", ex));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleException (final ConstraintViolationException ex) {
        return Flux.fromIterable(ex.getConstraintViolations())
                .map(e -> ErrorFieldResponse.builder()
                        .name(((PathImpl)e.getPropertyPath()).getLeafNode().getName())
                        .message(getMessage(e.getMessageTemplate()))
                        .build())
                .collectList()
                .flatMap(list -> getExceptionResponse(BAD_REQUEST, null, list))
                .doFirst(() -> log.error("There was a constraint error: ", ex));
    }

    private String getMessage(String error) {
        return messageSource.getMessage(error, null, Locale.getDefault());
    }

    private Mono<ResponseEntity<ExceptionResponse>> getExceptionResponse
            (final HttpStatus status, final String message, final List<ErrorFieldResponse> messages) {
        final var response = ExceptionResponse.builder()
                .fields(messages)
                .errorDescription(message)
                .status(status.value())
                .build();
        return Mono.just(ResponseEntity.status(status).body(response));
    }
}

