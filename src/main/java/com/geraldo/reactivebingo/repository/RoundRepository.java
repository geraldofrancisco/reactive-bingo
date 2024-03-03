package com.geraldo.reactivebingo.repository;

import com.geraldo.reactivebingo.domain.model.document.round.RoundDocument;
import com.geraldo.reactivebingo.domain.model.enums.RoundStatus;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface RoundRepository extends ReactiveMongoRepository<RoundDocument, ObjectId> {
    Mono<Long> countByStatus(RoundStatus status);
    Mono<Boolean> existsById(ObjectId id);
    Flux<RoundDocument> findByStatus(RoundStatus status, Pageable pageable);
}
