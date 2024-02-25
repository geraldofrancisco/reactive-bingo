package com.geraldo.reactivebingo.domain.service;

import com.geraldo.reactivebingo.domain.mapper.PlayerMapper;
import com.geraldo.reactivebingo.domain.model.dto.Player;
import com.geraldo.reactivebingo.repository.PlayerRepository;
import com.geraldo.reactivebingo.rest.exception.BusinessException;
import com.geraldo.reactivebingo.rest.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.PLAYER_ALREADY_REGISTERED;
import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.PLAYER_NOT_FOUND;
import static java.lang.Boolean.FALSE;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository repository;
    private final PlayerMapper mapper;

    public Mono<Player> create(String nickname) {
        return this.repository.existsByNickname(nickname)
                .filter(FALSE::equals)
                .map(b -> mapper.toPlayer(nickname))
                .flatMap(this::save)
                .switchIfEmpty(Mono.error(new BusinessException(PLAYER_ALREADY_REGISTERED)))
                .doFirst(() -> log.info("Trying to create player"));
    }

    private Mono<Player> save(Player player) {
        return Mono.just(player)
                .map(mapper::toDocument)
                .flatMap(repository::save)
                .map(mapper::toPlayer)
                .doFirst(() -> log.info("Saving player in database"));
    }

    public Mono<Player> getById(String id) {
        return Mono.just(id)
                .filter(ObjectId::isValid)
                .map(ObjectId::new)
                .flatMap(repository::findById)
                .map(mapper::toPlayer)
                .switchIfEmpty(Mono.error(new NotFoundException(PLAYER_NOT_FOUND)))
                .doFirst(() -> log.info("Trying to search for player by id"));

    }
}
