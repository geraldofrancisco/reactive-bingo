package com.geraldo.reactivebingo.domain.model.response.round;

import lombok.Builder;

import java.util.List;
@Builder(toBuilder = true)
public record RoundCardOnlyResponse(
        String roundId,
        List<Integer> numbers,
        RoundPlayerResponse owner
) {
}
