package com.geraldo.reactivebingo.domain.service;

import com.geraldo.reactivebingo.domain.execute.DrawExecutor;
import com.geraldo.reactivebingo.domain.execute.GenerateCardExecutor;
import com.geraldo.reactivebingo.domain.mapper.RoundMapper;
import com.geraldo.reactivebingo.domain.model.dto.round.Round;
import com.geraldo.reactivebingo.domain.model.dto.round.RoundCard;
import com.geraldo.reactivebingo.domain.model.enums.RoundStatus;
import com.geraldo.reactivebingo.domain.validate.GenerateCardValidate;
import com.geraldo.reactivebingo.repository.RoundRepository;
import com.geraldo.reactivebingo.rest.exception.BusinessException;
import com.geraldo.reactivebingo.rest.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.ROUND_LAST_NUMBER_DRAWN_NOT_RUNNING;
import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.ROUND_NOT_FOUND;
import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.ROUND_NUMBER_OF_INVALID_CARDS_FOR_THE_ROUND;
import static com.geraldo.reactivebingo.domain.model.enums.RoundStatus.RUNNING;

@Slf4j
@Service
@AllArgsConstructor
public class RoundService {
    private RoundMapper mapper;
    private RoundRepository repository;
    private PlayerService playerService;
    private GenerateCardValidate generateCardValidate;
    private GenerateCardExecutor generateCardExecutor;
    private DrawExecutor drawExecutor;

    public Mono<PageImpl<Round>> findALlByStatus(RoundStatus status, Pageable pageable) {
        return this.repository.countByStatus(status)
            .flatMap(total -> this.repository.findByStatus(status, pageable)
                .map(this.mapper::toRound)
                .collectList()
                .map(list -> new PageImpl<>(list, pageable, total))
            )
            .doFirst(() -> log.info("Search round by status in a paginated way"));
    }

    public Mono<Round> getById(String id) {
        return Mono.just(id)
            .map(ObjectId::new)
            .flatMap(repository::findById)
            .map(mapper::toRound)
            .switchIfEmpty(Mono.error(new NotFoundException(ROUND_NOT_FOUND)))
            .doFirst(() -> log.info("Trying to search for round by id"));

    }

    public Mono<Round> create() {
        return Mono.just(Round.builder().build())
            .flatMap(this::save)
            .doFirst(() -> log.info("Creating new round record"));
    }

    public Mono<Integer> getTheLastNumberDrawnByTheRoundId(String id) {
        return getById(id)
            .filter(round -> RUNNING.equals(round.getStatus()))
            .map(Round::getLastDraw)
            .switchIfEmpty(Mono.error(new BusinessException(ROUND_LAST_NUMBER_DRAWN_NOT_RUNNING)));
    }

    public Mono<Round> drawNextNumberByTheRoundId(String id) {
        return getById(id)
            .filter(r -> r.getCards().size() > 1)
            .flatMap(r -> Mono.just(r)
                .filter(round -> round.getWinners().isEmpty())
                .flatMap(drawExecutor::execute)
                .flatMap(this::save)
                .defaultIfEmpty(r)
            )
            .switchIfEmpty(Mono.error(new BusinessException(ROUND_NUMBER_OF_INVALID_CARDS_FOR_THE_ROUND)));
    }

    public Mono<Pair<RoundCard, String>> generateCard(String roundId, String playerId) {
        return getById(roundId)
            .flatMap(round -> playerService.getById(playerId)
                .flatMap(player -> generateCardValidate.validate(round, player))
                .flatMap(generateCardExecutor::execute)
                .flatMap(pair -> Mono.just(pair.getLeft())
                    .flatMap(this::save)
                    .map(saved -> Pair.of(pair.getRight(), saved.getId()))
                )
            );
    }

    private Mono<Round> save(Round round) {
        return Mono.just(round)
            .map(mapper::toDocument)
            .flatMap(repository::save)
            .map(mapper::toRound);
    }


}
