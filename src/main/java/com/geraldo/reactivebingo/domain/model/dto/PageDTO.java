package com.geraldo.reactivebingo.domain.model.dto;

import lombok.Builder;
import org.springframework.data.domain.Pageable;

import java.util.List;

public record PageDTO<T>(
        List<T> response,
        Pageable pageable,
        Long total
) {
    @Builder(toBuilder = true)
    public PageDTO {
    }
}
