package com.geraldo.reactivebingo.domain.validate;

import com.geraldo.reactivebingo.domain.model.dto.player.Player;
import com.geraldo.reactivebingo.domain.model.dto.round.Round;
import com.geraldo.reactivebingo.rest.exception.BusinessException;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.ROUND_CREATE_CARD_EXISTS_CARD_FOR_PLAYER;
import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.ROUND_CREATE_CARD_INVALID_STATUS;
import static com.geraldo.reactivebingo.domain.model.enums.RoundStatus.CREATED;

@Component
public class GenerateCardValidate {

    public Mono<Pair<Round, Player>> validate(Round round, Player player) {
        return Mono.just(Pair.of(round, player))
                .flatMap(this::validateStatus)
                .flatMap(this::validateExistsPlayer);
    }

    private Mono<Pair<Round, Player>> validateStatus(Pair<Round, Player> pair){
        if(!CREATED.equals(pair.getLeft().getStatus()))
            return Mono.error(new BusinessException(ROUND_CREATE_CARD_INVALID_STATUS));

        return Mono.just(pair);
    }

    private  Mono<Pair<Round, Player>> validateExistsPlayer(Pair<Round, Player> pair) {
        var round = pair.getLeft();
        var player = pair.getRight();

        if(round.getCards().stream().anyMatch(card -> card.getOwner().getId().equals(player.id())))
            return Mono.error(new BusinessException(ROUND_CREATE_CARD_EXISTS_CARD_FOR_PLAYER));

        return Mono.just(pair);
    }
}
