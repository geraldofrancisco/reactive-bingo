package com.geraldo.reactivebingo.domain.model.dto.player;

import lombok.Builder;

@Builder(toBuilder = true)
public record Player(
        String id,
        String nickname
) {
}
