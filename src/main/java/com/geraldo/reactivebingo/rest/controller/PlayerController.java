package com.geraldo.reactivebingo.rest.controller;

import com.geraldo.reactivebingo.domain.mapper.PlayerMapper;
import com.geraldo.reactivebingo.domain.model.exception.ExceptionResponse;
import com.geraldo.reactivebingo.domain.model.request.player.PlayerUpdateRequest;
import com.geraldo.reactivebingo.domain.model.response.player.PageResponse;
import com.geraldo.reactivebingo.domain.model.response.player.PlayerResponse;
import com.geraldo.reactivebingo.domain.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.geraldo.reactivebingo.domain.constants.Constants.DEFAULT_PAGE;
import static com.geraldo.reactivebingo.domain.constants.Constants.DEFAULT_SIZE;
import static com.geraldo.reactivebingo.domain.constants.Constants.EXCEPTION_CODE_BAD_REQUEST;
import static com.geraldo.reactivebingo.domain.constants.Constants.EXCEPTION_CODE_NOT_FOUND;
import static com.geraldo.reactivebingo.domain.constants.Constants.EXCEPTION_CODE_UNPROCESSABLE_ENTITY;
import static com.geraldo.reactivebingo.domain.constants.Constants.ID;
import static com.geraldo.reactivebingo.domain.constants.Constants.NICKNAME;
import static com.geraldo.reactivebingo.domain.constants.Constants.PAGE;
import static com.geraldo.reactivebingo.domain.constants.Constants.SIZE;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PAGE_DESCRIPTION;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PLAYER_CONTROLLER;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PLAYER_CONTROLLER_DESCRIPTION;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PLAYER_DELETE_DESCRIPTION;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PLAYER_DELETE_SUMMARY;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PLAYER_FIELD_ID_DESCRIPTION;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PLAYER_FIELD_NICKNAME_DESCRIPTION;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PLAYER_GET_BY_ID_DESCRIPTION;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PLAYER_GET_BY_ID_SUMMARY;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PLAYER_GET_LIST_DESCRIPTION;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PLAYER_GET_LIST_SUMMARY;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PLAYER_POST_DESCRIPTION;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PLAYER_POST_SUMMARY;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PLAYER_PUT_DESCRIPTION;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PLAYER_PUT_SUMMARY;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.SIZE_DESCRIPTION;
import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.GENERIC_REQUIRED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/player")
@Validated
@RequiredArgsConstructor
@Tag(name = PLAYER_CONTROLLER, description = PLAYER_CONTROLLER_DESCRIPTION)
@ApiResponses({
    @ApiResponse(
        responseCode = EXCEPTION_CODE_BAD_REQUEST,
        content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ExceptionResponse.class)
        )
    ),
    @ApiResponse(
        responseCode = EXCEPTION_CODE_NOT_FOUND,
        content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ExceptionResponse.class)
        )
    )
    ,
    @ApiResponse(
        responseCode = EXCEPTION_CODE_UNPROCESSABLE_ENTITY,
        content = @Content(
            mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ExceptionResponse.class)
        )
    )
})
public class PlayerController {

    private final PlayerService service;
    private final PlayerMapper mapper;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    @Operation(
        summary = PLAYER_GET_LIST_SUMMARY,
        description = PLAYER_GET_LIST_DESCRIPTION,
        parameters = {
                @Parameter(name = PAGE, description = PAGE_DESCRIPTION),
                @Parameter(name = SIZE, description = SIZE_DESCRIPTION)
        },
        responses = @ApiResponse(
            responseCode = EXCEPTION_CODE_BAD_REQUEST,
            content = @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponse.class)
            )
        )
    )
    public Mono<PageResponse<PlayerResponse>> getList(
            @RequestParam(name = PAGE, defaultValue = DEFAULT_PAGE, required = false) int page,
            @RequestParam(name = SIZE, defaultValue = DEFAULT_SIZE, required = false) int size
    ) {
        return service.findALl(PageRequest.of(page, size))
                .map(mapper::toPage);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    @Operation(
        summary = PLAYER_GET_BY_ID_SUMMARY,
        description = PLAYER_GET_BY_ID_DESCRIPTION,
        parameters = @Parameter(name = ID, description = PLAYER_FIELD_ID_DESCRIPTION)
    )
    public Mono<PlayerResponse> getById(
            @PathVariable
            @NotBlank(message = GENERIC_REQUIRED)
            String id
    ) {
        return service.getById(id)
                .map(mapper::toResponse);
    }

    @PostMapping(value = "/{nickname}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    @Operation(
        summary = PLAYER_POST_SUMMARY,
        description = PLAYER_POST_DESCRIPTION,
        parameters = @Parameter(name = NICKNAME, description = PLAYER_FIELD_NICKNAME_DESCRIPTION)
    )
    public Mono<PlayerResponse> create(
            @PathVariable
            @NotBlank(message = GENERIC_REQUIRED)
            String nickname
    ) {
        return service.create(nickname)
                .map(mapper::toResponse);
    }

    @PutMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    @Operation(
        summary = PLAYER_PUT_SUMMARY,
        description = PLAYER_PUT_DESCRIPTION
    )
    public Mono<PlayerResponse> update(
            @Valid
            @RequestBody
            PlayerUpdateRequest request
    ) {
       return Mono.just(request)
               .map(mapper::toPlayer)
               .flatMap(service::update)
               .map(mapper::toResponse);

    }

    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    @Operation(
        summary = PLAYER_DELETE_SUMMARY,
        description = PLAYER_DELETE_DESCRIPTION,
        parameters = @Parameter(name = ID, description = PLAYER_FIELD_ID_DESCRIPTION)
    )
    public Mono<Void> delete(
            @PathVariable String id
    ) {
        return service.delete(id);
    }
}
