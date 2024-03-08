package com.geraldo.reactivebingo.domain.constants;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ErrorMessages {
    //GENERICS
    public static final String GENERIC_ERROR = "GENERIC_ERROR";
    public static final String GENERIC_REQUIRED = "GENERIC_REQUIRED";
    public static final String GENERIC_INVALID_OBJECT_ID = "GENERIC_INVALID_OBJECT_ID";

    //PLAYER
    public static final String PLAYER_ALREADY_REGISTERED = "PLAYER_ALREADY_REGISTERED";
    public static final String PLAYER_NOT_FOUND = "PLAYER_NOT_FOUND";
    public static final String PLAYER_ID_INVALID = "PLAYER_ID_INVALID";

    public static final String ROUND_STATUS_INVALID = "ROUND_STATUS_INVALID";
    public static final String ROUND_NOT_FOUND = "ROUND_NOT_FOUND";
    public static final String ROUND_CREATE_CARD_INVALID_STATUS = "ROUND_CREATE_CARD_INVALID_STATUS";
    public static final String ROUND_CREATE_CARD_EXISTS_CARD_FOR_PLAYER = "ROUND_CREATE_CARD_EXISTS_CARD_FOR_PLAYER";
    public static final String ROUND_LAST_NUMBER_DRAWN_NOT_RUNNING = "ROUND_LAST_NUMBER_DRAWN_NOT_RUNNING";
}
