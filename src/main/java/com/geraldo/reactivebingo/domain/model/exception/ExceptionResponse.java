package com.geraldo.reactivebingo.domain.model.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record ExceptionResponse(
        LocalDateTime timestamp,
        String errorDescription,
        List<ErrorFieldResponse> fields,
        Integer status
) {
    @Builder(toBuilder = true)
    public ExceptionResponse {
        timestamp = LocalDateTime.now();
    }
}
