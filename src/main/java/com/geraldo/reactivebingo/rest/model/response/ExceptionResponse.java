package com.geraldo.reactivebingo.rest.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record ExceptionResponse(
        LocalDateTime timestamp,
        String errorDescription,
        List<ErrorFieldResponse> fields
) {
    @Builder(toBuilder = true)
    public ExceptionResponse {
        timestamp = LocalDateTime.now();
    }
}
