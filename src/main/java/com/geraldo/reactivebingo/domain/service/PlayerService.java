package com.geraldo.reactivebingo.domain.service;

import com.geraldo.reactivebingo.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository repository;


}
