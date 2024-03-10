package com.geraldo.reactivebingo.domain.constants;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Descriptions {

    //GENERIC
    public static final String PAGE_DESCRIPTION = "Define a página atual da consulta";
    public static final String SIZE_DESCRIPTION = "Define o tamanho da consulta";

    //PAGE
    public static final String PAGE_RESPONSE_CONTENT = "Lista de retorno da consulta";
    public static final String PAGE_RESPONSE_LAST = "Indica se é o último registro";
    public static final String PAGE_RESPONSE_TOTAL_PAGES = "Indica o total de páginas que possui a consulta";
    public static final String PAGE_RESPONSE_TOTAL_ELEMENTS = "Indica a quantidade total de elementos da consulta";
    public static final String PAGE_RESPONSE_HAS_NEXT = "Indica se existe uma próxima pagina";
    public static final String PAGE_RESPONSE_NUMBER = "Indica o número da página atual";
    public static final String PAGE_RESPONSE_SIZE = "Indica o tamanho da página atual";

    //EXCEPTION
    public static final String EXCEPTION_RESPONSE_TIMESTAMP = "Data/hora da exceção";
    public static final String EXCEPTION_RESPONSE_ERROR_DESCRIPTION = "Descrição da exceção lançada";
    public static final String EXCEPTION_RESPONSE_LIST_FIELD = "Lista com a descrição dos campos que possuem algum erro";
    public static final String EXCEPTION_RESPONSE_HTTP_STATUS = "Http status da exceção";
    public static final String EXCEPTION_FIELD_RESPONSE_NAME = "Nome do campo ou validação da exceção";
    public static final String EXCEPTION_FIELD_RESPONSE_MESSAGE = "Mensagem descrevendo o motivo da exceção";

    //PLAYER
    public static final String PLAYER_CONTROLLER = "Controlador de jogadores";
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


    //ROUND
    public static final String ROUND_CONTROLLER = "Controlador de rodadas";
    public static final String ROUND_CONTROLLER_DESCRIPTION = "Controlador para operações de criação e execução do bingo";
    public static final String ROUND_GET_LIST_SUMMARY = "Endpoint para listar rodadas";
    public static final String ROUND_GET_LIST_DESCRIPTION = "Realiza consulta das rodadas geradas pelo status de forma paginada";
    public static final String ROUND_GET_BY_ID_SUMMARY = "Endpoint para buscar uma rodada";
    public static final String ROUND_GET_BY_ID_DESCRIPTION = "Realiza consulta da rodada pelo id";
    public static final String ROUND_GET_LAST_NUMBER_SUMMARY = "Endpoint para buscar ultimo número gerado";
    public static final String ROUND_GET_LAST_NUMBER_DESCRIPTION = "Realiza consulta do último número gerado pelo id da rodada";
    public static final String ROUND_CREATE_SUMMARY = "Endpoint para criação da rodada";
    public static final String ROUND_CREATE_DESCRIPTION = "Realiza criação da rodada";
    public static final String ROUND_GENERATE_NUMBER_SUMMARY = "Endpoint para gerar um número";
    public static final String ROUND_GENERATE_NUMBER_DESCRIPTION = "Gera um novo número e verifica se houve um ou mais vencedores";
    public static final String ROUND_GENERATE_CARD_SUMMARY = "Endpoint para gerar um cartão";
    public static final String ROUND_GENERATE_CARD_DESCRIPTION = "Gera um novo cartão para um jogador para uma determinada rodada";
    public static final String ROUND_STATUS_DESCRIPTION = "Status da rodada. <br/><br/> Default value: CREATED";
    public static final String ROUND_ID_DESCRIPTION = "Identificador da rodada";
    public static final String ROUND_DRAWN_NUMBERS = "Números aleatórios sorteados";
    public static final String ROUND_WINNERS = "Vencedor(es) da rodada";
    public static final String ROUND_CARDS = "Cartões cadastrados para a rodada";
    public static final String ROUND_LAST_NUMBER = "Último número sorteado da rodada";
    public static final String ROUND_QTD_DRAWN_NUMBERS = "Quantidade de números sorteados nessa rodada";
    public static final String ROUND_CARD_NUMBERS = "20 números aleatórios do cartão";
    public static final String ROUND_CARD_OWNER = "Dono do cartão";
    public static final String ROUND_PLAYER_NICKNAME = "Nome/Apelido do jogador";
    public static final String ROUND_PLAYER_ID = "Identificador do jogador";
}
