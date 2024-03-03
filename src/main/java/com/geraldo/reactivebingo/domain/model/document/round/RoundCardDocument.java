package com.geraldo.reactivebingo.domain.model.document.round;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record RoundCardDocument(
        RoundPlayerDocument owner,
        List<Integer> numbers

) {
}
