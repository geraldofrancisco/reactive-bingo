package com.geraldo.reactivebingo.domain.model.response.round;

import com.geraldo.reactivebingo.domain.model.enums.RoundStatus;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record RoundDrawResponse(
        Integer lastDraw,
        Integer qtdDrawnNumbers,
        List<Integer> drawnNumbers,
        List<RoundCardResponse> winners,
        RoundStatus status
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
