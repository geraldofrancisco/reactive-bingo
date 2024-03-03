package com.geraldo.reactivebingo.rest.controller;

import com.geraldo.reactivebingo.domain.mapper.PlayerMapper;
import com.geraldo.reactivebingo.domain.model.dto.player.Player;
import com.geraldo.reactivebingo.domain.model.request.player.PlayerUpdateRequest;
import com.geraldo.reactivebingo.domain.model.response.PageResponse;
import com.geraldo.reactivebingo.domain.model.response.player.PlayerResponse;
import com.geraldo.reactivebingo.domain.service.PlayerService;
import com.geraldo.reactivebingo.rest.exception.ReactiveBingoExceptionHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.geraldo.reactivebingo.domain.constants.Constants.PAGE;
import static com.geraldo.reactivebingo.domain.constants.Constants.SIZE;
import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.GENERIC_REQUIRED;
import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.PLAYER_ID_INVALID;
import static com.geraldo.reactivebingo.domain.constants.Examples.PLAYER_ID_EXAMPLE;
import static com.geraldo.reactivebingo.domain.constants.Examples.PLAYER_NICKNAME_EXAMPLE;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mapstruct.factory.Mappers.getMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PlayerController.class, ReactiveBingoExceptionHandler.class})
@WebFluxTest(controllers = PlayerController.class)
public class PlayerControllerTests extends ControllerTest{

    @InjectMocks
    private PlayerController controller;

    @MockBean
    private PlayerService service;

    @MockBean
    private PlayerMapper mapper;


    private static final String PLAYER_URL = "/api/v1/player";
    private static final String PLAYER_URL_ID = "/api/v1/player/{id}";
    private static final String PLAYER_URL_NICKNAME = "/api/v1/player/{nickname}";

    @Override
    public void init() {
        super.init();
        setField(controller, "mapper", getMapper(PlayerMapper.class));
        setField(controller, "service", service);
    }

    @Test
    public void getListSuccessTest() {

        var uri = UriComponentsBuilder
                .fromUriString(PLAYER_URL)
                .queryParam(PAGE, 0)
                .queryParam(SIZE, 10)
                        .toUriString();

        when(service.findALl(any()))
                .thenReturn(Mono.just(getReturnSuccessList()));

        when(mapper.toPage(any()))
                .thenReturn(getReturnSuccessPageResponse());

        this.client
            .get()
            .uri(uri)
            .exchange()
            .expectStatus()
            .isOk();
    }

    @Test
    public void getByIdSuccessTest() {

        var uri = UriComponentsBuilder
                .fromUriString(PLAYER_URL_ID)
                .buildAndExpand(PLAYER_ID_EXAMPLE)
                .toUriString();

        when(service.getById(anyString()))
                .thenReturn(Mono.just(getPlayer()));

        when(mapper.toResponse(any()))
                .thenReturn(getPlayerResponse());


        this.client
            .get()
            .uri(uri)
            .exchange()
            .expectStatus()
            .isOk();
    }

    @Test
    public void createSuccessTest() {

        var uri = UriComponentsBuilder
                .fromUriString(PLAYER_URL_NICKNAME)
                .buildAndExpand(PLAYER_NICKNAME_EXAMPLE)
                .toUriString();

        when(service.create(anyString()))
                .thenReturn(Mono.just(getPlayer()));

        when(mapper.toResponse(any()))
                .thenReturn(getPlayerResponse());


        this.client
            .post()
            .uri(uri)
            .exchange()
            .expectStatus()
            .isCreated();
    }

    @Test
    public void updateSuccessTest() {

        when(mapper.toPlayer(any(PlayerUpdateRequest.class)))
                .thenReturn(getPlayer());


        when(service.update(any()))
                .thenReturn(Mono.just(getPlayer()));

        when(mapper.toResponse(any()))
                .thenReturn(getPlayerResponse());


        this.client
            .put()
            .uri(PLAYER_URL)
            .bodyValue(getUpdateRequest().build())
            .exchange()
            .expectStatus()
            .isOk();
    }

    @Test
    public void deleteSuccessTest() {

        var uri = UriComponentsBuilder
                .fromUriString(PLAYER_URL_ID)
                .buildAndExpand(PLAYER_ID_EXAMPLE)
                .toUriString();

        when(service.delete(any()))
                .thenReturn(Mono.empty());

        this.client
            .delete()
            .uri(uri)
            .exchange()
            .expectStatus()
            .isNoContent();
    }

    @Test
    public void errorUpdateRequest() {
        assertTrue(this.violation(getUpdateRequest().id("123").build()).stream().anyMatch(u -> u.getMessageTemplate().equals(PLAYER_ID_INVALID)));
        assertTrue(this.violation(getUpdateRequest().id(null).build()).stream().anyMatch(u -> u.getMessageTemplate().equals(GENERIC_REQUIRED)));
        assertTrue(this.violation(getUpdateRequest().nickname(null).build()).stream().anyMatch(u -> u.getMessageTemplate().equals(GENERIC_REQUIRED)));
    }

    private PlayerUpdateRequest.PlayerUpdateRequestBuilder getUpdateRequest() {
        return PlayerUpdateRequest.builder()
                .id(PLAYER_ID_EXAMPLE)
                .nickname(PLAYER_NICKNAME_EXAMPLE);
    }

    private PlayerResponse getPlayerResponse() {
        return PlayerResponse
                .builder()
                .id(PLAYER_ID_EXAMPLE)
                .nickname(PLAYER_NICKNAME_EXAMPLE)
                .build();
    }

    private Player getPlayer() {
        return Player.builder()
                .id(PLAYER_ID_EXAMPLE)
                .nickname(PLAYER_NICKNAME_EXAMPLE)
                .build();
    }

    private PageResponse<PlayerResponse> getReturnSuccessPageResponse() {
        var list = List.of(getPlayerResponse());

        return new PageResponse<>(list, true, 1, 1L, false, 0, 1);
    }

    private PageImpl<Player> getReturnSuccessList() {
        var list = List.of(getPlayer());
        var pageable = PageRequest.of(0,10);
        return new PageImpl<>(list,pageable,1);
    }
}
