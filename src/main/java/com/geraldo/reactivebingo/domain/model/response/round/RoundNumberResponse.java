package com.geraldo.reactivebingo.domain.model.response.round;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static com.geraldo.reactivebingo.domain.constants.Descriptions.ROUND_LAST_NUMBER;

@Builder(toBuilder = true)
public record RoundNumberResponse(
        @Schema(description = ROUND_LAST_NUMBER)
        Integer number
) {
}
