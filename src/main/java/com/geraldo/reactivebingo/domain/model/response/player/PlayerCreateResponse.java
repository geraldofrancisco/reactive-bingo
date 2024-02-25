package com.geraldo.reactivebingo.domain.model.response.player;

import lombok.Builder;

public record PlayerCreateResponse(
        String id,
        String nickname
) {
    @Builder(toBuilder = true)
    public PlayerCreateResponse {
    }
}
