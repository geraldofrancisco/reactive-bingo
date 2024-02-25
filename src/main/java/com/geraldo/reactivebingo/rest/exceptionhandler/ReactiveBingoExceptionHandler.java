package com.geraldo.reactivebingo.rest.exceptionhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geraldo.reactivebingo.domain.constants.ErrorMessages;
import com.geraldo.reactivebingo.rest.model.response.ExceptionResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.Locale;

import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.GENERIC_ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Order(-2)
@Component
@AllArgsConstructor
public class ReactiveBingoExceptionHandler implements WebExceptionHandler {
    private ObjectMapper mapper;
    private MessageSource messageSource;
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        return Mono.error(ex)
                .onErrorResume(Exception.class, e -> handlerException(exchange, e))
                .then();
    }

    private Mono<Void> handlerException(ServerWebExchange exchange, Exception exception) {
        return prepareExchange(exchange, INTERNAL_SERVER_ERROR)
            .map(ex -> GENERIC_ERROR)
            .map(this::getMessage)
            .map(this::buildError)
            .flatMap(response -> writeResponse(exchange, response))
            .doFirst(() -> log.error("Some generic error happened:", exception));
    }

    private Mono<ServerWebExchange> prepareExchange(final ServerWebExchange exchange, final HttpStatus status) {
        return Mono.just(exchange)
            .map(ex -> {
                exchange.getResponse().setStatusCode(status);
                exchange.getResponse().getHeaders().setContentType(APPLICATION_JSON);
                return ex;
            });
    }

    private Mono<Void> writeResponse(final ServerWebExchange exchange, final ExceptionResponse response) {
        return exchange.getResponse()
                .writeWith(Mono.fromCallable(() -> new DefaultDataBufferFactory()
                        .wrap(mapper.writeValueAsBytes(response)))
                );
    }

    private String getMessage(String error) {
        return messageSource.getMessage(error, null, Locale.getDefault());
    }

    private ExceptionResponse buildError(String errorMessage) {
        return ExceptionResponse.builder()
                .errorDescription(errorMessage)
                .build();
    }
}
