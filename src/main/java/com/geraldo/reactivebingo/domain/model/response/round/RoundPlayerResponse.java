package com.geraldo.reactivebingo.domain.model.response.round;

import lombok.Builder;

@Builder(toBuilder = true)
public record RoundPlayerResponse(
        String id,
        String nickname
) {
}
