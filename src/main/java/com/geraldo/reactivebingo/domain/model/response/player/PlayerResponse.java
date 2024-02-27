package com.geraldo.reactivebingo.domain.model.response.player;

import lombok.Builder;

public record PlayerResponse(
        String id,
        String nickname
) {
    @Builder(toBuilder = true)
    public PlayerResponse {
    }
}
