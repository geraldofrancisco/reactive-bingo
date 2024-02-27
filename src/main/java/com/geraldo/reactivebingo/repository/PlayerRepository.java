package com.geraldo.reactivebingo.repository;

import com.geraldo.reactivebingo.domain.model.document.player.PlayerDocument;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PlayerRepository extends ReactiveMongoRepository<PlayerDocument, ObjectId> {
    Mono<Boolean> existsByNickname(String nickname);
    Flux<PlayerDocument> findByIdNotNullOrderByNicknameAsc(Pageable pageable);
}
