package com.geraldo.reactivebingo.rest.controller;

import com.geraldo.reactivebingo.domain.mapper.PlayerMapper;
import com.geraldo.reactivebingo.domain.model.response.player.PlayerCreateResponse;
import com.geraldo.reactivebingo.domain.service.PlayerService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.GENERIC_REQUIRED;

@RestController
@RequestMapping("/api/v1/player")
@Validated
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService service;
    private final PlayerMapper mapper;

    @GetMapping("/{id}")
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
