package com.geraldo.reactivebingo.domain.model.response.round;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static com.geraldo.reactivebingo.domain.constants.Descriptions.ROUND_PLAYER_ID;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.ROUND_PLAYER_NICKNAME;

@Builder(toBuilder = true)
public record RoundPlayerResponse(
        @Schema(description = ROUND_PLAYER_ID)
        String id,

        @Schema(description = ROUND_PLAYER_NICKNAME)
        String nickname
) {
}
