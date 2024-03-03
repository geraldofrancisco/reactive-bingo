package com.geraldo.reactivebingo.domain.model.document.player;

import lombok.Builder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder(toBuilder = true)
@Document(collection = "player")
public record PlayerDocument(
    @Id ObjectId id,
    String nickname
) {
}
