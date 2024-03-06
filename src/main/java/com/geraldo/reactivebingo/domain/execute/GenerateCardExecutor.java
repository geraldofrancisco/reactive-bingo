package com.geraldo.reactivebingo.domain.execute;

import com.geraldo.reactivebingo.domain.model.dto.player.Player;
import com.geraldo.reactivebingo.domain.model.dto.round.Round;
import com.geraldo.reactivebingo.domain.model.dto.round.RoundCard;
import com.geraldo.reactivebingo.domain.model.dto.round.RoundPlayer;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class GenerateCardExecutor {

    private Random random;

    public Mono<Pair<Round, RoundCard>> execute(Pair<Round, Player> playerPair) {
        return Mono.just(playerPair.getRight())
            .map(this::createCard)
            .flatMap(card -> this.generateNumbers(playerPair.getLeft(), card));
    }

    private Mono<Pair<Round, RoundCard>> generateNumbers(Round round, RoundCard card) {
        var generated = new ArrayList<Integer>();
        return Flux.create((FluxSink<Integer> sink) -> {
            IntStream.range(0,20).forEach(e -> {
                var next = generateNumber(generated, round.getCards());
                generated.add(next);
                sink.next(next);
            });
            sink.complete();
        })
        .sort()
        .collectList()
        .map(list -> card.getNumbers().addAll(list))
        .map(x -> round.getCards().add(card))
        .map(x -> Pair.of(round, card));
    }

    private RoundCard createCard(Player player) {
        return RoundCard.builder()
            .owner(RoundPlayer.builder()
                .id(player.id())
                .nickname(player.nickname())
                .build()
            )
            .build();
    }

    private Integer generateNumber(List<Integer> generated, List<RoundCard> roundCards) {
        var next = generateNewUnprecedentedNumber(generated);
        var list = new ArrayList<>(generated);
        list.add(next);

        if(roundCards.stream().anyMatch(rc -> rc.countsRepeatedNumbers(list) > 5))
            return generateNumber(generated, roundCards);
        return next;

    }

    private Integer generateNewUnprecedentedNumber(List<Integer> list) {
        var next = Integer.valueOf(random.nextInt(100));
        if (list.stream().anyMatch(next::equals))
            return generateNewUnprecedentedNumber(list);
        return next;
    }
}
