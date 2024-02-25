package com.geraldo.reactivebingo.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/player")
@RequiredArgsConstructor
public class PlayerController {
    @GetMapping
    public Mono<Void> teste() {
        return Mono.error(new Exception("teste"));
    }
}
