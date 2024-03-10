package com.geraldo.reactivebingo.domain.execute;

import com.geraldo.reactivebingo.domain.model.dto.player.Player;
import com.geraldo.reactivebingo.domain.model.dto.round.Round;
import com.geraldo.reactivebingo.domain.model.dto.round.RoundCard;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static com.geraldo.reactivebingo.domain.constants.Examples.ID_EXAMPLE;

@ExtendWith(MockitoExtension.class)
public class GenerateCardExecutorTest {
    @InjectMocks
    private GenerateCardExecutor executor;

    @Test
    void createCardTest() {
        var result = executor.execute(Pair.of(getRound(), getPlayer()));

        StepVerifier.create(result)
                .expectNextMatches(Objects::nonNull)
                .verifyComplete();
    }

    private Player getPlayer() {
        return Player.builder()
                .id(ID_EXAMPLE)
                .build();
    }

    private Round getRound() {
        return Round.builder()
                .drawnNumbers(new ArrayList<>(List.of(1,2,3)))
                .cards(new ArrayList<>(List.of(getCard())))
                .build();
    }

    private RoundCard getCard() {
        var drawnNumbers = IntStream.range(0,40).boxed().toList();
        return RoundCard.builder()
                .numbers(new ArrayList<>(drawnNumbers))
                .build();
    }
}
