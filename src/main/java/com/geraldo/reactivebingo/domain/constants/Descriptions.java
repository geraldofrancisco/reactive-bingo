package com.geraldo.reactivebingo.domain.constants;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Descriptions {

    //GENERIC
    public static final String PAGE_DESCRIPTION = "Define a página atual da consulta";
    public static final String SIZE_DESCRIPTION = "Define o tamanho da consulta";

    //PLAYER
    public static final String PLAYER_CONTROLLER = "Player Controller";
    public static final String PLAYER_CONTROLLER_DESCRIPTION = "Controlador para operações CRUD dos jogadores do bingo";
    public static final String PLAYER_GET_LIST_SUMMARY = "Endpoint para consulta de todos os jogadores";
    public static final String PLAYER_GET_LIST_DESCRIPTION = "Realiza a busca de forma paginada";
}
