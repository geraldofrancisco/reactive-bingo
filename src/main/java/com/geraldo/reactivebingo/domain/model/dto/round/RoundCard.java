package com.geraldo.reactivebingo.domain.model.dto.round;

import lombok.Builder;

import java.util.List;

@Builder
public class RoundCard {
    private RoundPlayer owner;
    private List<Integer> numbers;
}
