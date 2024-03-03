package com.geraldo.reactivebingo.domain.service;

import com.geraldo.reactivebingo.domain.mapper.RoundMapper;
import com.geraldo.reactivebingo.domain.model.dto.round.Round;
import com.geraldo.reactivebingo.domain.model.dto.round.RoundCard;
import com.geraldo.reactivebingo.domain.model.enums.RoundStatus;
import com.geraldo.reactivebingo.domain.model.response.round.RoundCardResponse;
import com.geraldo.reactivebingo.repository.RoundRepository;
import com.geraldo.reactivebingo.rest.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.ROUND_NOT_FOUND;

@Slf4j
@Service
@AllArgsConstructor
public class RoundService {
    private RoundMapper mapper;
    private RoundRepository repository;

    public Mono<PageImpl<Round>> findALlByStatus(RoundStatus status, Pageable pageable) {
        return this.repository.countByStatus(status)
            .flatMap(total -> this.repository.findByStatus(status, pageable)
                .map(this.mapper::toRound)
                .collectList()
                .map(list -> new PageImpl<>(list, pageable, total))
            );
    }

    public Mono<Round> getById(String id) {
        return Mono.just(id)
            .filter(ObjectId::isValid)
            .map(ObjectId::new)
            .flatMap(repository::findById)
            .map(mapper::toRound)
            .switchIfEmpty(Mono.error(new NotFoundException(ROUND_NOT_FOUND)))
            .doFirst(() -> log.info("Trying to search for round by id"));

    }

    public Mono<Round> create() {
        return Mono.empty();
    }

    public Mono<Integer> getTheLastNumberDrawnByTheRoundId(String id) {
        return Mono.empty();
    }

    public Mono<Integer> drawNexNumberByTheRoundId(String id) {
        return Mono.empty();
    }

    public Mono<Pair<RoundCard, ObjectId>> generateCard(String roundId, String playerId) {
        return Mono.empty();
    }
}
