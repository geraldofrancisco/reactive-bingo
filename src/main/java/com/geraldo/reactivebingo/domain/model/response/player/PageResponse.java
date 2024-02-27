package com.geraldo.reactivebingo.domain.model.response.player;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;
import java.util.Objects;

public record PageResponse<T>(
        @Schema(description = "Lista de retorno da consulta")
        List<T> content,

        @Schema(description = "Indica se é o último registro")
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
