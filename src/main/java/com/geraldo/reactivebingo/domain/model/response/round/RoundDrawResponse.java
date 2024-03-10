package com.geraldo.reactivebingo.domain.model.response.round;

import com.geraldo.reactivebingo.domain.model.enums.RoundStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

import static com.geraldo.reactivebingo.domain.constants.Descriptions.ROUND_DRAWN_NUMBERS;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.ROUND_LAST_NUMBER;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.ROUND_QTD_DRAWN_NUMBERS;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.ROUND_STATUS_DESCRIPTION;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.ROUND_WINNERS;

@Builder(toBuilder = true)
public record RoundDrawResponse(

        @Schema(description = ROUND_STATUS_DESCRIPTION)
        RoundStatus status,

        @Schema(description = ROUND_LAST_NUMBER)
        Integer lastDraw,

        @Schema(description = ROUND_QTD_DRAWN_NUMBERS)
        Integer qtdDrawnNumbers,

        @Schema(description = ROUND_WINNERS)
        List<RoundCardResponse> winners,

        @Schema(description = ROUND_DRAWN_NUMBERS)
        List<Integer> drawnNumbers


) {
    @Override
    public List<Integer> drawnNumbers() {
        return drawnNumbers.stream().sorted().toList();
    }

    @Override
    public Integer qtdDrawnNumbers() {
        return drawnNumbers.size();
    }
}
