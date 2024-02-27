package com.geraldo.reactivebingo.domain.constants;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Descriptions {

    //GENERIC
    public static final String PAGE_DESCRIPTION = "Define a página atual da consulta";
    public static final String SIZE_DESCRIPTION = "Define o tamanho da consulta";

    //EXCEPTION
    public static final String EXCEPTION_RESPONSE_TIMESTAMP = "Data/hora da exceção";
    public static final String EXCEPTION_RESPONSE_ERROR_DESCRIPTION = "Descrição da exceção lançada";
    public static final String EXCEPTION_RESPONSE_LIST_FIELD = "Lista com a descrição dos campos que possuem algum erro";
    public static final String EXCEPTION_RESPONSE_HTTP_STATUS = "Http status da exceção";
    public static final String EXCEPTION_FIELD_RESPONSE_NAME = "Nome do campo ou validação da exceção";
    public static final String EXCEPTION_FIELD_RESPONSE_MESSAGE = "Mensagem descrevendo o motivo da exceção";

    //PLAYER
    public static final String PLAYER_CONTROLLER = "Player Controller";
    public static final String PLAYER_CONTROLLER_DESCRIPTION = "Controlador para operações CRUD dos jogadores do bingo";
    public static final String PLAYER_FIELD_ID_DESCRIPTION = "Parâmetro de identificação do jogador";
    public static final String PLAYER_FIELD_NICKNAME_DESCRIPTION = "Parâmetro de nome/apelido do jogador";
    public static final String PLAYER_GET_LIST_SUMMARY = "Endpoint para consulta de todos os jogadores";
    public static final String PLAYER_GET_LIST_DESCRIPTION = "Realiza a busca de forma paginada";
    public static final String PLAYER_GET_BY_ID_SUMMARY = "Endpoint para consulta de jogador";
    public static final String PLAYER_GET_BY_ID_DESCRIPTION = "Realiza a busca pelo id do jogador";
    public static final String PLAYER_POST_SUMMARY = "Endpoint para criação de jogador";
    public static final String PLAYER_POST_DESCRIPTION = "Realiza a criação de jogador passando o apelido";
    public static final String PLAYER_PUT_SUMMARY = "Endpoint para atualização de jogador";
    public static final String PLAYER_PUT_DESCRIPTION = "Realiza a atualização pelo id do jogador";
    public static final String PLAYER_DELETE_SUMMARY = "Endpoint para exclusão de jogador";
    public static final String PLAYER_DELETE_DESCRIPTION = "Realiza a exclusão pelo id do jogador";
    public static final String PLAYER_FIELD_ID = "Código de identificação do jogador";
    public static final String PLAYER_FIELD_NICKNAME = "Nome de usuário do jogador";

}
