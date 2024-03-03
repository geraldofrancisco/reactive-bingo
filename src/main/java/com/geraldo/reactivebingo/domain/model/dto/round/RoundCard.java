package com.geraldo.reactivebingo.domain.model.dto.round;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class RoundCard {
    private RoundPlayer owner;
    @Builder.Default
    private List<Integer> numbers = new ArrayList<>();
}
