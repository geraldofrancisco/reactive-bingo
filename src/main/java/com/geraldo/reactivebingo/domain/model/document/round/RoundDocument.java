package com.geraldo.reactivebingo.domain.model.document.round;

import com.geraldo.reactivebingo.domain.model.enums.RoundStatus;
import lombok.Builder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder(toBuilder = true)
@Document(collection = "round")
public record RoundDocument(
        @Id ObjectId id,
        RoundStatus status,
        Integer lastDraw,
        List<Integer> drawnNumbers,
        RoundCardDocument winner,
        List<RoundCardDocument> cards
) {
}
