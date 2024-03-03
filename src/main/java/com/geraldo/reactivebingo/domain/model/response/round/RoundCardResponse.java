package com.geraldo.reactivebingo.domain.model.response.round;

import com.geraldo.reactivebingo.domain.model.response.player.PlayerResponse;
import lombok.Builder;

import java.util.List;
@Builder(toBuilder = true)
public record RoundCardResponse(
        String roundId,
        List<Integer> numbers,
        RoundPlayerResponse owner
) {
}
