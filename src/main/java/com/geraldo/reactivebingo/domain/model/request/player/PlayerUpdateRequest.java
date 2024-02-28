package com.geraldo.reactivebingo.domain.model.request.player;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import static com.geraldo.reactivebingo.domain.constants.Descriptions.PLAYER_FIELD_ID;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PLAYER_FIELD_NICKNAME;
import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.GENERIC_REQUIRED;
import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.PLAYER_ID_INVALID;

public record PlayerUpdateRequest(
        @Schema(description = PLAYER_FIELD_ID)
        @NotBlank(message = GENERIC_REQUIRED)
        String id,

        @Schema(description = PLAYER_FIELD_NICKNAME)
        @NotBlank(message = GENERIC_REQUIRED)
        String nickname
) {
        @Hidden
        @AssertTrue(message = PLAYER_ID_INVALID)
        public boolean isIdValid() {
                if(StringUtils.isEmpty(id))
                        return true;

                return ObjectId.isValid(id);
        }

        @Builder(toBuilder = true)
        public PlayerUpdateRequest {
        }
}
