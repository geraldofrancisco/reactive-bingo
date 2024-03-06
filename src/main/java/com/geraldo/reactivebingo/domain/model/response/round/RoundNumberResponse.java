package com.geraldo.reactivebingo.domain.model.response.round;

import lombok.Builder;

@Builder(toBuilder = true)
public record RoundNumberResponse(
        Integer number
) {
}
