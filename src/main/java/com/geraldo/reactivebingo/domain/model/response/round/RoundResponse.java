package com.geraldo.reactivebingo.domain.model.response.round;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.geraldo.reactivebingo.domain.model.enums.RoundStatus;
import com.geraldo.reactivebingo.domain.model.response.player.PlayerResponse;
import lombok.Builder;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record RoundResponse(
        String id,
        RoundStatus status,
        List<Integer> drawnNumbers,
        PlayerResponse winner,
        List<PlayerResponse> cards

) {
    @Builder(toBuilder = true)
    public RoundResponse {
    }
}
