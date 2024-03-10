package com.geraldo.reactivebingo.domain.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

import static com.geraldo.reactivebingo.domain.constants.Descriptions.PAGE_RESPONSE_CONTENT;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PAGE_RESPONSE_HAS_NEXT;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PAGE_RESPONSE_LAST;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PAGE_RESPONSE_NUMBER;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PAGE_RESPONSE_SIZE;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PAGE_RESPONSE_TOTAL_ELEMENTS;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PAGE_RESPONSE_TOTAL_PAGES;

@Builder(toBuilder = true)
public record PageResponse<T>(
        @Schema(description = PAGE_RESPONSE_CONTENT)
        List<T> content,

        @Schema(description = PAGE_RESPONSE_LAST)
        boolean last,

        @Schema(description = PAGE_RESPONSE_TOTAL_PAGES)
        Integer totalPages,

        @Schema(description = PAGE_RESPONSE_TOTAL_ELEMENTS)
        Long totalElements,

        @Schema(description = PAGE_RESPONSE_HAS_NEXT)
        boolean hasNext,

        @Schema(description = PAGE_RESPONSE_NUMBER)
        Integer number,

        @Schema(description = PAGE_RESPONSE_SIZE)
        Integer size
) {
}
