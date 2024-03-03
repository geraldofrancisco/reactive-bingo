package com.geraldo.reactivebingo.repository;

import com.geraldo.reactivebingo.domain.model.document.round.RoundDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RoundRepository extends ReactiveMongoRepository<RoundDocument, ObjectId> {
    Mono<Boolean> existsById(ObjectId id);
}
