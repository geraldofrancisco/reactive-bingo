package com.geraldo.reactivebingo.domain.service;

import com.geraldo.reactivebingo.domain.mapper.RoundMapper;
import com.geraldo.reactivebingo.domain.model.dto.round.Round;
import com.geraldo.reactivebingo.domain.model.enums.RoundStatus;
import com.geraldo.reactivebingo.repository.RoundRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class RoundService {
    private RoundMapper mapper;
    private RoundRepository repository;

    public Mono<PageImpl<Round>> findALlByStatus(RoundStatus status, Pageable pageable) {
        return this.repository.countByStatus(status)
            .flatMap(total -> this.repository.findByStatus(status, pageable)
                .map(this.mapper::toRound)
                .collectList()
                .map(list -> new PageImpl<>(list, pageable, total))
            );
    }
}
