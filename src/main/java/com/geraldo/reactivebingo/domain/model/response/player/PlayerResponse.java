package com.geraldo.reactivebingo.domain.model.response.player;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static com.geraldo.reactivebingo.domain.constants.Descriptions.PLAYER_FIELD_ID;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PLAYER_FIELD_NICKNAME;

@Builder(toBuilder = true)
public record PlayerResponse(
        @Schema(description = PLAYER_FIELD_ID)
        String id,

        @Schema(description = PLAYER_FIELD_NICKNAME)
        String nickname
) {
}
