package com.geraldo.reactivebingo.rest.exceptionhandler;

import com.geraldo.reactivebingo.rest.model.response.ErrorFieldResponse;
import com.geraldo.reactivebingo.rest.model.response.ExceptionResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Locale;

import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.GENERIC_ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class ReactiveBingoExceptionHandler {
    private MessageSource messageSource;


    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleException
            (final Exception ex) {
        return Mono.just(getMessage(GENERIC_ERROR))
                .flatMap(message -> getExceptionResponse(INTERNAL_SERVER_ERROR, message, null))
                .doFirst(() -> log.error("There was a generic error: ", ex));
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

