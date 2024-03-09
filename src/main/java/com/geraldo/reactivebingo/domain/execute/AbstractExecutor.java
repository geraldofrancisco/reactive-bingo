package com.geraldo.reactivebingo.domain.execute;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Random;

@AllArgsConstructor
public abstract class AbstractExecutor {

    private Random random;
    public Integer generateNewUnprecedentedNumber(List<Integer> list) {
        var next = Integer.valueOf(random.nextInt(100));
        if (list.stream().anyMatch(next::equals))
            return generateNewUnprecedentedNumber(list);
        return next;
    }
}
