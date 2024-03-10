package com.geraldo.reactivebingo.rest.controller;

import com.geraldo.reactivebingo.domain.mapper.RoundMapper;
import com.geraldo.reactivebingo.domain.model.enums.RoundStatus;
import com.geraldo.reactivebingo.domain.model.exception.ExceptionResponse;
import com.geraldo.reactivebingo.domain.model.response.PageResponse;
import com.geraldo.reactivebingo.domain.model.response.round.RoundCardOnlyResponse;
import com.geraldo.reactivebingo.domain.model.response.round.RoundDrawResponse;
import com.geraldo.reactivebingo.domain.model.response.round.RoundNumberResponse;
import com.geraldo.reactivebingo.domain.model.response.round.RoundResponse;
import com.geraldo.reactivebingo.domain.service.RoundService;
import com.geraldo.reactivebingo.rest.controller.validate.ObjectIdIsValid;
import com.geraldo.reactivebingo.rest.controller.validate.ValueOfEnum;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import static com.geraldo.reactivebingo.domain.constants.Constants.PAGE;
import static com.geraldo.reactivebingo.domain.constants.Constants.SIZE;
import static com.geraldo.reactivebingo.domain.constants.Constants.STATUS;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.PAGE_DESCRIPTION;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.ROUND_CONTROLLER;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.ROUND_CONTROLLER_DESCRIPTION;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.ROUND_STATUS_DESCRIPTION;
import static com.geraldo.reactivebingo.domain.constants.Descriptions.SIZE_DESCRIPTION;
import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.GENERIC_REQUIRED;
import static com.geraldo.reactivebingo.domain.constants.ErrorMessages.ROUND_STATUS_INVALID;
import static com.geraldo.reactivebingo.domain.constants.Examples.ROUND_STATUS_EXAMPLE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/round")
@Validated
@RequiredArgsConstructor
@Tag(name = ROUND_CONTROLLER, description = ROUND_CONTROLLER_DESCRIPTION)
@ApiResponse(
    responseCode = EXCEPTION_CODE_BAD_REQUEST,
    content = @Content(
        mediaType = APPLICATION_JSON_VALUE,
        schema = @Schema(implementation = ExceptionResponse.class)
    )
)
@ApiResponse(
    responseCode = EXCEPTION_CODE_NOT_FOUND,
    content = @Content(
        mediaType = APPLICATION_JSON_VALUE,
        schema = @Schema(implementation = ExceptionResponse.class)
    )
)
@ApiResponse(
    responseCode = EXCEPTION_CODE_UNPROCESSABLE_ENTITY,
    content = @Content(
        mediaType = APPLICATION_JSON_VALUE,
        schema = @Schema(implementation = ExceptionResponse.class)
    )
)
public class RoundController {

    private final RoundService service;
    private final RoundMapper mapper;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public Mono<PageResponse<RoundResponse>> getList(
        @Parameter(name = PAGE, description = PAGE_DESCRIPTION)
        @RequestParam(name = PAGE, defaultValue = DEFAULT_PAGE, required = false) int page,
        @Parameter(name = SIZE, description = SIZE_DESCRIPTION)
        @RequestParam(name = SIZE, defaultValue = DEFAULT_SIZE, required = false) int size,
        @Parameter(name = STATUS, description = ROUND_STATUS_DESCRIPTION, example = ROUND_STATUS_EXAMPLE,
            schema = @Schema(implementation = RoundStatus.class, enumAsRef = true, defaultValue = ROUND_STATUS_EXAMPLE)
        )
        @Valid @ValueOfEnum(enumClass = RoundStatus.class, message = ROUND_STATUS_INVALID)
        @RequestParam(name = STATUS, defaultValue = ROUND_STATUS_EXAMPLE, required = false) String status
    ){
        return service.findALlByStatus(RoundStatus.getByName(status), PageRequest.of(page, size))
                .map(mapper::toPage);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public Mono<RoundResponse> getById(
        @PathVariable
        @ObjectIdIsValid
        @NotBlank(message = GENERIC_REQUIRED)
        String id
    ){
        return this.service.getById(id)
                .map(mapper::toResponse);
    }

    @GetMapping(value = "/{id}/last-number", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public Mono<RoundNumberResponse> getLastNumber(
        @PathVariable
        @ObjectIdIsValid
        @NotBlank(message = GENERIC_REQUIRED)
        String id
    ){
        return this.service.getTheLastNumberDrawnByTheRoundId(id)
                .map(mapper::toRoundNumberResponse);
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public Mono<RoundResponse> create() {
        return this.service.create()
            .map(mapper::toResponse);
    }

    @PostMapping(value = "/{id}/generate-number", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public Mono<RoundDrawResponse> generateNumber(
        @PathVariable
        @ObjectIdIsValid
        @NotBlank(message = GENERIC_REQUIRED)
        String id
    ) {
        return service.drawNextNumberByTheRoundId(id)
                .map(mapper::toRoundDrawResponse);
    }

    @PostMapping(value = "/{id}/generate-card/player/{playerId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public Mono<RoundCardOnlyResponse> generateCard(
        @PathVariable
        @NotBlank(message = GENERIC_REQUIRED)
        @ObjectIdIsValid
        String id,
        @PathVariable
        @NotBlank(message = GENERIC_REQUIRED)
        @ObjectIdIsValid
        String playerId
    ) {
        return this.service.generateCard(id, playerId)
            .map(mapper::toRoundCardResponse);
    }
}
