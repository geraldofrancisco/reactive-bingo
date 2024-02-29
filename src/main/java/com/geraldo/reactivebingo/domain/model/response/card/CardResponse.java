package com.geraldo.reactivebingo.domain.model.response.card;

import com.geraldo.reactivebingo.domain.model.response.player.PlayerResponse;
import lombok.Builder;

import java.util.List;

public record CardResponse(
        List<Integer> numbers,
        PlayerResponse player
) {
    @Builder(toBuilder = true)
    public CardResponse {
    }
}
