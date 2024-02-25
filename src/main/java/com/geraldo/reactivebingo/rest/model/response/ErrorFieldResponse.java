package com.geraldo.reactivebingo.rest.model.response;

import lombok.Builder;

public record ErrorFieldResponse(
        String name,
        String message
) {
    @Builder(toBuilder = true)
    public ErrorFieldResponse {
    }
}
