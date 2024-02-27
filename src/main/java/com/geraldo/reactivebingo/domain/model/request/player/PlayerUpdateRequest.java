package com.geraldo.reactivebingo.domain.model.request.player;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.GENERIC_REQUIRED;
import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.PLAYER_ID_INVALID;

public record PlayerUpdateRequest(
        @NotBlank(message = GENERIC_REQUIRED)
        String id,

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
}
