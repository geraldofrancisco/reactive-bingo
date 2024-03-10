package com.geraldo.reactivebingo.domain.service;

import com.geraldo.reactivebingo.domain.mapper.PlayerMapper;
import com.geraldo.reactivebingo.domain.model.document.player.PlayerDocument;
import com.geraldo.reactivebingo.domain.model.dto.player.Player;
import com.geraldo.reactivebingo.repository.PlayerRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.PLAYER_ALREADY_REGISTERED;
import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.PLAYER_NOT_FOUND;
import static com.geraldo.reactivebingo.domain.constants.Examples.PLAYER_NICKNAME_EXAMPLE;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PlayerServiceTests {

    @InjectMocks
    private PlayerService service;

    @Mock
    private PlayerRepository repository;

    @Mock
    private PlayerMapper mapper;


    @Test
    void createSuccessTest() {
        var player = getPlayer();
        when(repository.existsByNickname(anyString()))
                .thenReturn(Mono.just(FALSE));

        when(mapper.toPlayer(anyString()))
                .thenReturn(player);

        saveTraining(player);

        var result = this.service.create(PLAYER_NICKNAME_EXAMPLE);

        StepVerifier.create(result)
            .expectNext(player)
            .verifyComplete();
    }

    @Test
    void createErrorTest() {
        when(repository.existsByNickname(anyString()))
                .thenReturn(Mono.just(TRUE));

        var result = this.service.create(PLAYER_NICKNAME_EXAMPLE);
        StepVerifier.create(result)
            .expectErrorMessage(PLAYER_ALREADY_REGISTERED)
            .verify();
    }

    @Test
    void getByIdSuccessTest() {
        var player = getPlayer();

        when(repository.findById(any(ObjectId.class)))
                .thenReturn(Mono.just(getPlayerDocument()));

        when(mapper.toPlayer(any(PlayerDocument.class)))
                .thenReturn(player);

        var result = service.getById(player.id());

        StepVerifier.create(result)
            .expectNext(player)
            .verifyComplete();
    }

    @Test
    void getByIdErrorTest() {
        var player = getPlayer();

        when(repository.findById(any(ObjectId.class)))
                .thenReturn(Mono.empty());

        var result = service.getById(player.id());

        StepVerifier.create(result)
            .expectErrorMessage(PLAYER_NOT_FOUND)
            .verify();
    }

    @Test
    void findAllTest() {

        var pageable = PageRequest.of(0, 1);
        var player = getPlayer();
        var total = 1L;
        var list = List.of(player);
        var page = new PageImpl<>(list, pageable, total);

        when(repository.count())
                .thenReturn(Mono.just(total));

        when(repository.findByIdNotNullOrderByNicknameAsc(any(Pageable.class)))
                .thenReturn(Flux.just(getPlayerDocument()));

        when(mapper.toPlayer(any(PlayerDocument.class)))
                .thenReturn(player);

        var result = service.findALl(pageable);

        StepVerifier.create(result)
            .expectNext(page)
            .verifyComplete();
    }

    @Test
    void updateSuccessTest() {
        var player = getPlayer();
        when(repository.existsById(any(ObjectId.class)))
                .thenReturn(Mono.just(TRUE));

        saveTraining(player);

        var result = service.update(player);

        StepVerifier.create(result)
            .expectNext(player)
            .verifyComplete();
    }

    @Test
    void updateErrorTest() {
        var player = getPlayer();
        when(repository.existsById(any(ObjectId.class)))
                .thenReturn(Mono.just(FALSE));

        var result = service.update(player);

        StepVerifier.create(result)
                .expectErrorMessage(PLAYER_NOT_FOUND)
                .verify();
    }

    @Test
    void getDeleteSuccessTest() {
        var player = getPlayer();

        when(repository.findById(any(ObjectId.class)))
                .thenReturn(Mono.just(getPlayerDocument()));

        when(mapper.toPlayer(any(PlayerDocument.class)))
                .thenReturn(player);

        when(mapper.toDocument(any(Player.class)))
                .thenReturn(getPlayerDocument());

        when(repository.delete(any(PlayerDocument.class)))
                .thenReturn(Mono.empty());

        var result = service.delete(player.id());

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void deleteErrorTest() {
        var player = getPlayer();

        when(repository.findById(any(ObjectId.class)))
                .thenReturn(Mono.empty());

        var result = service.delete(player.id());

        StepVerifier.create(result)
                .expectErrorMessage(PLAYER_NOT_FOUND)
                .verify();
    }

    private void saveTraining(Player player) {
        when(mapper.toDocument(any()))
                .thenReturn(getPlayerDocument());

        when(repository.save(any()))
                .thenReturn(Mono.just(getPlayerDocument()));

        when(mapper.toPlayer(any(PlayerDocument.class)))
                .thenReturn(player);
    }

    private PlayerDocument getPlayerDocument() {
        return PlayerDocument.builder()
                .id(new ObjectId())
                .nickname(PLAYER_NICKNAME_EXAMPLE)
                .build();
    }

    private Player getPlayer() {
        return Player.builder()
            .id(new ObjectId().toHexString())
            .nickname(PLAYER_NICKNAME_EXAMPLE)
            .build();
    }
}
