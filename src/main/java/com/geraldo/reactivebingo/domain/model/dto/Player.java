package com.geraldo.reactivebingo.domain.model.dto;

import lombok.Builder;

public record Player(
        String id,
        String nickname
) {
    @Builder(toBuilder = true)
    public Player {
    }
}
