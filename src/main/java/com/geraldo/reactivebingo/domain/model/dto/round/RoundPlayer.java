package com.geraldo.reactivebingo.domain.model.dto.round;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RoundPlayer {
    private String id;
    private String nickname;
}
