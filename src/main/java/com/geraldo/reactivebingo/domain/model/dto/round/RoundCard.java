package com.geraldo.reactivebingo.domain.model.dto.round;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Builder
@Data
public class RoundCard {
    private RoundPlayer owner;
    @Builder.Default
    private List<Integer> numbers = new ArrayList<>();

    public Integer countsRepeatedNumbers(List<Integer> list) {
        var count = new AtomicInteger();
        list.forEach(n -> {
            var exist = numbers.stream().anyMatch(number -> number.equals(n));
            if (exist)
                count.incrementAndGet();
        });
        return count.get();
    }
}
