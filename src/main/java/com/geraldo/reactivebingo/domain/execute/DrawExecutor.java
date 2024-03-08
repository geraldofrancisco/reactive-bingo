package com.geraldo.reactivebingo.domain.execute;

import com.geraldo.reactivebingo.domain.model.dto.round.Round;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;

import static com.geraldo.reactivebingo.domain.model.enums.RoundStatus.FINISHED;
import static com.geraldo.reactivebingo.domain.model.enums.RoundStatus.RUNNING;

@Component
public class DrawExecutor extends AbstractExecutor {
    public DrawExecutor(Random random) {
        super(random);
    }

    public Mono<Round> execute(Round round) {
        return Mono.just(round)
            .flatMap(this::drawNumber)
            .flatMap(this::thereAreChampions);
    }

    private Mono<Round> thereAreChampions(Round round) {
        return Flux.fromIterable(round.getCards())
            .filter(card -> card.isWinner(round.getDrawnNumbers()))
            .collectList()
            .filter(winners -> !winners.isEmpty())
            .map(winners -> {
                round.getWinners().addAll(winners);
                round.setStatus(FINISHED);
                return round;
            })
            .defaultIfEmpty(round);
    }

    private Mono<Round> drawNumber(Round round) {
        return Mono.just(this.generateNewUnprecedentedNumber(round.getDrawnNumbers()))
            .map(number ->{
                round.setStatus(RUNNING);
                round.setLastDraw(number);
                round.getDrawnNumbers().add(number);
                return round;
            });
    }
}
