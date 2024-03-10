package com.geraldo.reactivebingo.domain.model.response.round;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

import static com.geraldo.reactivebingo.domain.constants.Descriptions.ROUND_CARD_NUMBERS;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.ROUND_CARD_OWNER;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.ROUND_ID_DESCRIPTION;

@Builder(toBuilder = true)
public record RoundCardOnlyResponse(
        @Schema(description = ROUND_ID_DESCRIPTION)
        String roundId,

        @Schema(description = ROUND_CARD_NUMBERS)
        List<Integer> numbers,

        @Schema(description = ROUND_CARD_OWNER)
        RoundPlayerResponse owner
) {
}
