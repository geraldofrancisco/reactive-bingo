package com.geraldo.reactivebingo.domain.model.response.round;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.geraldo.reactivebingo.domain.model.enums.RoundStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.ROUND_CARDS;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.ROUND_DRAWN_NUMBERS;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.ROUND_ID_DESCRIPTION;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.ROUND_STATUS_DESCRIPTION;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.ROUND_WINNERS;

@Builder(toBuilder = true)
@JsonInclude(NON_NULL)
public record RoundResponse(

        @Schema(description = ROUND_ID_DESCRIPTION)
        String id,

        @Schema(description = ROUND_STATUS_DESCRIPTION)
        RoundStatus status,

        @Schema(description = ROUND_DRAWN_NUMBERS)
        List<Integer> drawnNumbers,

        @Schema(description = ROUND_WINNERS)
        List<RoundCardResponse> winners,

        @Schema(description = ROUND_CARDS)
        List<RoundCardResponse> cards

) {
}
