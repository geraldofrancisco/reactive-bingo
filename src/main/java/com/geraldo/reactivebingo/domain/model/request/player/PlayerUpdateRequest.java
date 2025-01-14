package com.geraldo.reactivebingo.domain.model.request.player;

import com.geraldo.reactivebingo.rest.controller.validate.ObjectIdIsValid;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import static com.geraldo.reactivebingo.domain.constants.Descriptions.PLAYER_FIELD_ID;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PLAYER_FIELD_NICKNAME;
import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.GENERIC_REQUIRED;

@Builder(toBuilder = true)
public record PlayerUpdateRequest(
        @Schema(description = PLAYER_FIELD_ID)
        @NotBlank(message = GENERIC_REQUIRED)
        @ObjectIdIsValid
        String id,

        @Schema(description = PLAYER_FIELD_NICKNAME)
        @NotBlank(message = GENERIC_REQUIRED)
        String nickname
) {
}
