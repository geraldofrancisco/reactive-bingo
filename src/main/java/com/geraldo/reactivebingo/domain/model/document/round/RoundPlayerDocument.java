package com.geraldo.reactivebingo.domain.model.document.round;

import lombok.Builder;

@Builder(toBuilder = true)
public record RoundPlayerDocument(
        String id,
        String nickname
) {
}
