package com.geraldo.reactivebingo.rest.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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



    @PostMapping("/{nickname}")
    public Mono<String> create(
            @PathVariable
            @NotBlank(message = GENERIC_REQUIRED)
            String nickname
    ) {
        return Mono.just(nickname);
    }
}
