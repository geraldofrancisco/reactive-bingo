package com.geraldo.reactivebingo.domain.execute;

import com.geraldo.reactivebingo.domain.model.dto.player.Player;
import com.geraldo.reactivebingo.domain.model.dto.round.Round;
import com.geraldo.reactivebingo.domain.model.dto.round.RoundCard;
import com.geraldo.reactivebingo.domain.model.dto.round.RoundPlayer;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Random;

@Component
@AllArgsConstructor
public class GenerateCardExecutor {

    private Random random;
    public Mono<Pair<Round,RoundCard>> execute(Pair<Round, Player> playerPair) {
        return Mono.just(playerPair.getRight())
                .map(this::createCard)
                .flatMap(card -> this.generateNumbers(playerPair.getLeft(), card));
    }

    private Mono<Pair<Round, RoundCard>> generateNumbers(Round round, RoundCard card) {
        return Mono.empty();
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
}
