package com.geraldo.reactivebingo.rest.controller;

import com.geraldo.reactivebingo.domain.mapper.RoundMapper;
import com.geraldo.reactivebingo.domain.model.dto.player.Player;
import com.geraldo.reactivebingo.domain.model.dto.round.Round;
import com.geraldo.reactivebingo.domain.model.dto.round.RoundCard;
import com.geraldo.reactivebingo.domain.model.response.PageResponse;
import com.geraldo.reactivebingo.domain.model.response.player.PlayerResponse;
import com.geraldo.reactivebingo.domain.model.response.round.RoundCardOnlyResponse;
import com.geraldo.reactivebingo.domain.model.response.round.RoundDrawResponse;
import com.geraldo.reactivebingo.domain.model.response.round.RoundNumberResponse;
import com.geraldo.reactivebingo.domain.model.response.round.RoundResponse;
import com.geraldo.reactivebingo.domain.service.RoundService;
import com.geraldo.reactivebingo.rest.exception.ReactiveBingoExceptionHandler;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.geraldo.reactivebingo.domain.constants.Examples.ID_EXAMPLE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RoundController.class, ReactiveBingoExceptionHandler.class})
@WebFluxTest(controllers = RoundController.class)
public class RoundControllerTest extends ControllerTest {

    @MockBean
    private RoundService service;

    @MockBean
    private RoundMapper mapper;

    private static final String ROUND_URL = "/api/v1/round";
    private static final String ROUND_URL_ID = "/api/v1/round/{id}";
    private static final String ROUND_URL_LAST_NUMBER = "/api/v1/round/{id}/last-number";
    private static final String ROUND_URL_GENERATE_NUMBER = "/api/v1/round/{id}/generate-number";
    private static final String ROUND_URL_GENERATE_CARD = "/api/v1/round/{id}/generate-card/player/{playerId}";

    @Test
    public void getAllSuccessTest() {

        when(service.findALlByStatus(any(), any()))
            .thenReturn(Mono.just(getReturnSuccessList()));
        when(mapper.toPage(any()))
            .thenReturn(getReturnSuccessPageResponse());

        this.client
            .get()
            .uri(ROUND_URL)
            .exchange()
            .expectStatus()
            .isOk();
    }

    @Test
    public void getByIdSuccessTest() {
        when(service.getById(anyString()))
                .thenReturn(Mono.just(getRound()));
        when(mapper.toResponse(any()))
                .thenReturn(getResponse());

        var uri = UriComponentsBuilder
            .fromUriString(ROUND_URL_ID)
            .buildAndExpand(ID_EXAMPLE)
            .toUriString();

        this.client
            .get()
            .uri(uri)
            .exchange()
            .expectStatus()
            .isOk();

    }

    @Test
    public void getByIdInvalidIdErrorTest() {

        var uri = UriComponentsBuilder
            .fromUriString(ROUND_URL_ID)
            .buildAndExpand("xpto")
            .toUriString();

        this.client
            .get()
            .uri(uri)
            .exchange()
            .expectStatus()
            .isBadRequest();

    }

    @Test
    public void getByIdBlankIdErrorTest() {

        var uri = UriComponentsBuilder
            .fromUriString(ROUND_URL_ID)
            .buildAndExpand(" ")
            .toUriString();

        this.client
            .get()
            .uri(uri)
            .exchange()
            .expectStatus()
            .isBadRequest();

    }

    @Test
    public void createSuccessTest() {
        when(service.create())
                .thenReturn(Mono.just(getRound()));
        when(mapper.toResponse(any()))
                .thenReturn(getResponse());

        this.client
            .post()
            .uri(ROUND_URL)
            .exchange()
            .expectStatus()
            .isOk();
    }

    @Test
    public void getLastNumberSuccessTest() {
        when(service.getTheLastNumberDrawnByTheRoundId(anyString()))
                .thenReturn(Mono.just(123));
        when(mapper.toRoundNumberResponse(any()))
                .thenReturn(getNumberResponse());

        var uri = UriComponentsBuilder
            .fromUriString(ROUND_URL_LAST_NUMBER)
            .buildAndExpand(ID_EXAMPLE)
            .toUriString();

        this.client
            .get()
            .uri(uri)
            .exchange()
            .expectStatus()
            .isOk();

    }

    @Test
    public void getLastNumberInvalidIdErrorTest() {

        var uri = UriComponentsBuilder
            .fromUriString(ROUND_URL_LAST_NUMBER)
            .buildAndExpand("xpto")
            .toUriString();

        this.client
            .get()
            .uri(uri)
            .exchange()
            .expectStatus()
            .isBadRequest();

    }

    @Test
    public void getLastNumberBlankIdErrorTest() {

        var uri = UriComponentsBuilder
            .fromUriString(ROUND_URL_LAST_NUMBER)
            .buildAndExpand(" ")
            .toUriString();

        this.client
            .get()
            .uri(uri)
            .exchange()
            .expectStatus()
            .isBadRequest();
    }

    @Test
    public void generateNumberSuccessTest() {
        when(service.drawNextNumberByTheRoundId(anyString()))
                .thenReturn(Mono.just(getRound()));
        when(mapper.toRoundDrawResponse(any()))
                .thenReturn(getDrawResponse());

        var uri = UriComponentsBuilder
            .fromUriString(ROUND_URL_GENERATE_NUMBER)
            .buildAndExpand(ID_EXAMPLE)
            .toUriString();

        this.client
            .post()
            .uri(uri)
            .exchange()
            .expectStatus()
            .isOk();

    }

    @Test
    public void generateNumberInvalidIdErrorTest() {

        var uri = UriComponentsBuilder
                .fromUriString(ROUND_URL_GENERATE_NUMBER)
                .buildAndExpand("xpto")
                .toUriString();

        this.client
            .post()
            .uri(uri)
            .exchange()
            .expectStatus()
            .isBadRequest();

    }

    @Test
    public void generateNumberBlankIdErrorTest() {

        var uri = UriComponentsBuilder
                .fromUriString(ROUND_URL_GENERATE_NUMBER)
                .buildAndExpand(" ")
                .toUriString();

        this.client
            .post()
            .uri(uri)
            .exchange()
            .expectStatus()
            .isBadRequest();
    }

    @Test
    public void generateCardSuccessTest() {
        when(service.generateCard(anyString(), anyString()))
                .thenReturn(Mono.just(generateCardResponse()));
        when(mapper.toRoundCardResponse(any()))
                .thenReturn(getRoundCardOnlyResponse());

        var uri = UriComponentsBuilder
            .fromUriString(ROUND_URL_GENERATE_CARD)
            .buildAndExpand(ID_EXAMPLE, ID_EXAMPLE)
            .toUriString();

        this.client
            .post()
            .uri(uri)
            .exchange()
            .expectStatus()
            .isOk();

    }

    @Test
    public void generateCardInvalidIdErrorTest() {

        var uri = UriComponentsBuilder
            .fromUriString(ROUND_URL_GENERATE_CARD)
            .buildAndExpand("xpto", "xpto")
            .toUriString();

        this.client
            .post()
            .uri(uri)
            .exchange()
            .expectStatus()
            .isBadRequest();

    }

    @Test
    public void generateCardRoundBlankIdErrorTest() {

        var uri = UriComponentsBuilder
            .fromUriString(ROUND_URL_GENERATE_CARD)
            .buildAndExpand(" ", ID_EXAMPLE)
            .toUriString();

        this.client
            .post()
            .uri(uri)
            .exchange()
            .expectStatus()
            .isBadRequest();
    }

    @Test
    public void generateCardPlayerBlankIdErrorTest() {

        var uri = UriComponentsBuilder
                .fromUriString(ROUND_URL_GENERATE_CARD)
                .buildAndExpand(ID_EXAMPLE, " ")
                .toUriString();

        this.client
            .post()
            .uri(uri)
            .exchange()
            .expectStatus()
            .isBadRequest();
    }

    private Pair<RoundCard, String> generateCardResponse() {
        return Pair.of(RoundCard.builder().build(), ID_EXAMPLE);
    }

    private RoundCardOnlyResponse getRoundCardOnlyResponse() {
        return RoundCardOnlyResponse.builder().build();
    }

    private RoundDrawResponse getDrawResponse() {
        return RoundDrawResponse.builder()
            .winners(List.of())
            .drawnNumbers(List.of())
            .build();
    }

    private RoundNumberResponse getNumberResponse() {
        return RoundNumberResponse.builder().number(123).build();
    }

    private PageResponse<RoundResponse> getReturnSuccessPageResponse() {
        var list = List.of(getResponse());

        return new PageResponse<>(list, true, 1, 1L, false, 0, 1);
    }

    private PageImpl<Round> getReturnSuccessList() {
        var list = List.of(getRound());
        var pageable = PageRequest.of(0,10);
        return new PageImpl<>(list,pageable,1);
    }

    private Round getRound() {
        return Round.builder().build();
    }

    private RoundResponse getResponse() {
        return RoundResponse.builder().build();
    }
}
