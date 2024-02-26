package com.geraldo.reactivebingo.domain.model.response.player;

import lombok.Builder;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        boolean last,
        Integer totalPages
) {
    @Builder(toBuilder = true)
    public PageResponse {
    }

}
