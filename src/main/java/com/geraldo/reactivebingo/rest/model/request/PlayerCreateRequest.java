package com.geraldo.reactivebingo.rest.model.request;

import jakarta.validation.constraints.NotBlank;

public record PlayerCreateRequest(
        @NotBlank(message = "xpto") String name
) {
}
