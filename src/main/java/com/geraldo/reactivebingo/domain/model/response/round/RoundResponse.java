package com.geraldo.reactivebingo.domain.model.response.round;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.geraldo.reactivebingo.domain.model.document.round.RoundCardDocument;
import com.geraldo.reactivebingo.domain.model.enums.RoundStatus;
import com.geraldo.reactivebingo.domain.model.response.player.PlayerResponse;
import lombok.Builder;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder(toBuilder = true)
@JsonInclude(NON_NULL)
public record RoundResponse(
        String id,
        RoundStatus status,
        List<Integer> drawnNumbers,
        RoundCardResponse winner,
        List<RoundCardResponse> cards

) {
}
