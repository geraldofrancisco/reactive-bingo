package com.geraldo.reactivebingo.rest.controller;

import com.geraldo.reactivebingo.domain.mapper.PlayerMapper;
import com.geraldo.reactivebingo.domain.model.response.player.PageResponse;
import com.geraldo.reactivebingo.domain.model.response.player.PlayerCreateResponse;
import com.geraldo.reactivebingo.domain.service.PlayerService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.geraldo.reactivebingo.domain.constants.Constants.DEFAULT_PAGE;
import static com.geraldo.reactivebingo.domain.constants.Constants.DEFAULT_SIZE;
import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.GENERIC_REQUIRED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/player")
@Validated
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService service;
    private final PlayerMapper mapper;

    @GetMapping
    @ResponseStatus(OK)
    public Mono<PageResponse<PlayerCreateResponse>> getList(
            @RequestParam(name = "page", defaultValue = DEFAULT_PAGE, required = false) int page,
            @RequestParam(name = "size", defaultValue = DEFAULT_SIZE, required = false) int size
    ) {
        return service.findALl(PageRequest.of(page, size))
                .map(mapper::toPage);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public Mono<PlayerCreateResponse> getById(
            @PathVariable
            @NotBlank(message = GENERIC_REQUIRED)
            String id
    ) {
        return service.getById(id)
                .map(mapper::toResponse);
    }

    @PostMapping("/{nickname}")
    public Mono<PlayerCreateResponse> create(
            @PathVariable
            @NotBlank(message = GENERIC_REQUIRED)
            String nickname
    ) {
        return service.create(nickname)
                .map(mapper::toResponse);
    }
}
