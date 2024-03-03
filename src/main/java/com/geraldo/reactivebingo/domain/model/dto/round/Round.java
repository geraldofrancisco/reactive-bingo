package com.geraldo.reactivebingo.domain.model.dto.round;

import com.geraldo.reactivebingo.domain.model.enums.RoundStatus;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static com.geraldo.reactivebingo.domain.model.enums.RoundStatus.CREATED;

@Builder
@Data
public class Round {
    private String id;
    @Builder.Default
    private RoundStatus status = CREATED;
    @Builder.Default
    private List<Integer> drawnNumbers = new ArrayList<>();
    private RoundCard winner;
    @Builder.Default
    List<RoundCard> cards = new ArrayList<>();
}
