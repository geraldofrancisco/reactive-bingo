package com.geraldo.reactivebingo.domain.model.response.round;

import com.geraldo.reactivebingo.domain.model.enums.RoundStatus;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record RoundDrawResponse(
        RoundStatus status,
        Integer lastDraw,
        Integer qtdDrawnNumbers,
        List<RoundCardResponse> winners,
        List<Integer> drawnNumbers


) {
    @Override
    public List<Integer> drawnNumbers() {
        return drawnNumbers.stream().sorted().toList();
    }

    @Override
    public Integer qtdDrawnNumbers() {
        return drawnNumbers.size();
    }
}
