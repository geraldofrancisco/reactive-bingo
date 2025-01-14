package com.geraldo.reactivebingo.domain.service;

import com.geraldo.reactivebingo.domain.execute.DrawExecutor;
import com.geraldo.reactivebingo.domain.execute.GenerateCardExecutor;
import com.geraldo.reactivebingo.domain.mapper.RoundMapper;
import com.geraldo.reactivebingo.domain.model.document.round.RoundDocument;
import com.geraldo.reactivebingo.domain.model.dto.player.Player;
import com.geraldo.reactivebingo.domain.model.dto.round.Round;
import com.geraldo.reactivebingo.domain.model.dto.round.RoundCard;
import com.geraldo.reactivebingo.domain.model.dto.round.RoundPlayer;
import com.geraldo.reactivebingo.domain.validate.GenerateCardValidate;
import com.geraldo.reactivebingo.repository.RoundRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.ROUND_CREATE_CARD_EXISTS_CARD_FOR_PLAYER;
import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.ROUND_CREATE_CARD_INVALID_STATUS;
import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.ROUND_LAST_NUMBER_DRAWN_NOT_RUNNING;
import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.ROUND_NOT_FOUND;
import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.ROUND_NUMBER_OF_INVALID_CARDS_FOR_THE_ROUND;
import static com.geraldo.reactivebingo.domain.constants.Examples.ID_EXAMPLE;
import static com.geraldo.reactivebingo.domain.model.enums.RoundStatus.CREATED;
import static com.geraldo.reactivebingo.domain.model.enums.RoundStatus.FINISHED;
import static com.geraldo.reactivebingo.domain.model.enums.RoundStatus.RUNNING;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoundServiceTests {
    @InjectMocks
    private RoundService service;

    @Mock
    private RoundRepository repository;

    @Mock
    private RoundMapper mapper;

    @Mock
    private PlayerService playerService;

    @Mock
    private GenerateCardValidate generateCardValidate;

    @Mock
    private  GenerateCardExecutor generateCardExecutor;

    @Mock
    private DrawExecutor drawExecutor;


    @Test
    void findALlByStatusSuccessTest() {
        var pageable = PageRequest.of(0, 1);
        var total = 1L;
        var round = getRound().build();
        var page = new PageImpl<>(List.of(round), pageable, total);

        when(repository.countByStatus(any())).thenReturn(Mono.just(total));
        when(repository.findByStatus(any(), any())).thenReturn(Flux.just(getRoundDocument()));
        when(mapper.toRound(any())).thenReturn(round);

        var result = service.findALlByStatus(CREATED, pageable);

        StepVerifier.create(result)
            .expectNext(page)
            .verifyComplete();
    }

    @Test
    void getByIdSuccessTest() {
        var round = getRound().build();
        getByIdSuccessTraining(round);

        var result = service.getById(ID_EXAMPLE);

        StepVerifier.create(result)
            .expectNext(round)
            .verifyComplete();
    }

    @Test
    void getByIdSuccessErrorTest() {
        when(repository.findById(any(ObjectId.class)))
            .thenReturn(Mono.empty());

        var result = service.getById(ID_EXAMPLE);

        StepVerifier.create(result)
            .expectErrorMessage(ROUND_NOT_FOUND)
            .verify();
    }

    @Test
    void createTest() {
        var round = getRound().build();
        saveTraining(round);
        var result = service.create();

        StepVerifier.create(result)
            .expectNext(round)
            .verifyComplete();
    }

    @Test
    void getTheLastNumberDrawnByTheRoundIdSuccessTest() {
        var round = getRound()
                .lastDraw(1)
                .status(RUNNING)
                .build();

        getByIdSuccessTraining(round);

        var result = service.getTheLastNumberDrawnByTheRoundId(ID_EXAMPLE);

        StepVerifier.create(result)
            .expectNext(round.getLastDraw())
            .verifyComplete();
    }

    @Test
    void getTheLastNumberDrawnByTheRoundIdErrorTest() {
        var round = getRound()
            .lastDraw(1)
            .status(FINISHED)
            .build();

        getByIdSuccessTraining(round);

        var result = service.getTheLastNumberDrawnByTheRoundId(ID_EXAMPLE);

        StepVerifier.create(result)
            .expectErrorMessage(ROUND_LAST_NUMBER_DRAWN_NOT_RUNNING)
            .verify();
    }

    @Test
    void drawNextNumberByTheRoundIdContainsWinnerSuccessTest() {
        var round = getRound()
            .winners(List.of(getRoundCard().build()))
            .cards(List.of(getRoundCard().build(), getRoundCard().build()))
            .build();

        getByIdSuccessTraining(round);

        var result = service.drawNextNumberByTheRoundId(ID_EXAMPLE);

        StepVerifier.create(result)
            .expectNext(round)
            .verifyComplete();
    }

    @Test
    void drawNextNumberByTheRoundIdExecuteNotWinnerSuccessTest() {
        var round = getRound()
            .cards(List.of(getRoundCard().build(), getRoundCard().build()))
            .build();

        getByIdSuccessTraining(round);
        when(drawExecutor.execute(any()))
                .thenReturn(Mono.just(round));
        saveTraining(round);

        var result = service.drawNextNumberByTheRoundId(ID_EXAMPLE);

        StepVerifier.create(result)
            .expectNextMatches(t -> t.getWinners().isEmpty())
            .verifyComplete();
    }

    @Test
    void drawNextNumberByTheRoundIdExecuteWinnerSuccessTest() {
        var round = getRound()
                .cards(List.of(getRoundCard().build(), getRoundCard().build()))
                .winners(List.of(getRoundCard().build()))
                .build();

        getByIdSuccessTraining(round);

        var result = service.drawNextNumberByTheRoundId(ID_EXAMPLE);

        StepVerifier.create(result)
                .expectNextMatches(t -> !t.getWinners().isEmpty())
                .verifyComplete();
    }

    @Test
    void drawNextNumberByTheRoundIdNumberOfCardsInvalidErrorTest() {
        var round = getRound()
            .cards(List.of(getRoundCard().build()))
            .build();

        getByIdSuccessTraining(round);

        var result = service.drawNextNumberByTheRoundId(ID_EXAMPLE);

        StepVerifier.create(result)
            .expectErrorMessage(ROUND_NUMBER_OF_INVALID_CARDS_FOR_THE_ROUND)
            .verify();
    }

    @Test
    void generateCardSuccessTest() {
        var round = getRound().status(CREATED).build();
        var player = getPlayer().build();

        getByIdSuccessTraining(round);
        playerGetByIdTraining(player);
        when(generateCardValidate.validate(any(), any()))
                .thenCallRealMethod();
        when(generateCardExecutor.execute(any()))
                .thenReturn(Mono.just(Pair.of(round, getRoundCard().build())));
        saveTraining(round);

        var result = service.generateCard(ID_EXAMPLE, ID_EXAMPLE);

        StepVerifier.create(result)
            .expectNextMatches(Objects::nonNull)
            .verifyComplete();
    }

    @Test
    void generateCardRoundInvalidStatusErrorTest() {
        var round = getRound().status(RUNNING).build();
        var player = getPlayer().build();

        getByIdSuccessTraining(round);
        playerGetByIdTraining(player);
        when(generateCardValidate.validate(any(), any()))
                .thenCallRealMethod();

        var result = service.generateCard(ID_EXAMPLE, ID_EXAMPLE);

        StepVerifier.create(result)
            .expectErrorMessage(ROUND_CREATE_CARD_INVALID_STATUS)
            .verify();
    }

    @Test
    void generateCardRoundExistsPlayerErrorTest() {
        var player = getPlayer()
                .id(ID_EXAMPLE)
                .build();

        var cards = List.of(getRoundCard()
                .owner(getRoundPlayer())
                .build());

        var round = getRound()
                .status(CREATED)
                .cards(cards)
                .build();

        getByIdSuccessTraining(round);
        playerGetByIdTraining(player);
        when(generateCardValidate.validate(any(), any()))
                .thenCallRealMethod();

        var result = service.generateCard(ID_EXAMPLE, ID_EXAMPLE);

        StepVerifier.create(result)
                .expectErrorMessage(ROUND_CREATE_CARD_EXISTS_CARD_FOR_PLAYER)
                .verify();
    }

    private RoundPlayer getRoundPlayer() {
        return RoundPlayer.builder()
                .id(ID_EXAMPLE)
                .build();
    }

    private Player.PlayerBuilder getPlayer() {
        return Player.builder();
    }

    private RoundCard.RoundCardBuilder getRoundCard() {
        return RoundCard.builder()
            .numbers(getElements());
    }

    private List<Integer> getElements() {
        return new ArrayList<>(List.of(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20));
    }

    private void saveTraining(Round round) {
        when(mapper.toDocument(any())).thenReturn(getRoundDocument());
        when(repository.save(any())).thenReturn(Mono.just(getRoundDocument()));
        when(mapper.toRound(any())).thenReturn(round);
    }

    private void playerGetByIdTraining(Player player) {
        when(playerService.getById(anyString()))
                .thenReturn(Mono.just(player));
    }

    private void getByIdSuccessTraining(Round round) {
        when(repository.findById(any(ObjectId.class)))
                .thenReturn(Mono.just(getRoundDocument()));
        when(mapper.toRound(any())).thenReturn(round);
    }

    private RoundDocument getRoundDocument() {
        return RoundDocument.builder().build();
    }

    private Round.RoundBuilder getRound() {
        return Round.builder();
    }
}
