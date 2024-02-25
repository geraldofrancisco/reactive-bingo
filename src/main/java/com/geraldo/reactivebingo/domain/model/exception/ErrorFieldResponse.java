package com.geraldo.reactivebingo.domain.model.exception;

import lombok.Builder;

public record ErrorFieldResponse(
        String name,
        String message
) {
    @Builder(toBuilder = true)
    public ErrorFieldResponse {
    }
}
