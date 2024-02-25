package com.geraldo.reactivebingo.domain.constants;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ErrorMessages {
    //GENERICS
    public static final String GENERIC_ERROR = "GENERIC_ERROR";
    public static final String GENERIC_REQUIRED = "GENERIC_REQUIRED";

    //PLAYER
    public static final String PLAYER_ALREADY_REGISTERED = "PLAYER_ALREADY_REGISTERED";
    public static final String PLAYER_NOT_FOUND = "PLAYER_NOT_FOUND";
}
