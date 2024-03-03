package com.geraldo.reactivebingo.domain.constants;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Constants {
    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_SIZE = "30";
    public static final String PAGE = "page";
    public static final String SIZE = "size";
    public static final String ID = "id";
    public static final String NICKNAME = "nickname";
    public static final String STATUS = "status";
    public static final String EXCEPTION_CODE_BAD_REQUEST = "400";
    public static final String EXCEPTION_CODE_NOT_FOUND = "404";
    public static final String EXCEPTION_CODE_UNPROCESSABLE_ENTITY = "422";
}
