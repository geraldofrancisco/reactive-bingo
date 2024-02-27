package com.geraldo.reactivebingo.domain.model.response.player;

import lombok.Builder;

import java.util.List;
import java.util.Objects;

public record PageResponse<T>(
        List<T> content,
        boolean last,
        Integer totalPages,
        Long totalElements,
        boolean hasNext,
        Integer number,
        Integer size
) {
    @Builder(toBuilder = true)
    public PageResponse {
    }

    @Override
    public List<T> content() {
        if(Objects.isNull(content))
            return List.of();
        return content;
    }
}
