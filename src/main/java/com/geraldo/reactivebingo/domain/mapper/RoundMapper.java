package com.geraldo.reactivebingo.domain.mapper;

import com.geraldo.reactivebingo.domain.model.document.round.RoundDocument;
import com.geraldo.reactivebingo.domain.model.dto.player.Player;
import com.geraldo.reactivebingo.domain.model.dto.round.Round;
import com.geraldo.reactivebingo.domain.model.response.PageResponse;
import com.geraldo.reactivebingo.domain.model.response.player.PlayerResponse;
import com.geraldo.reactivebingo.domain.model.response.round.RoundResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface RoundMapper extends EntityMapper {
    @Mapping(target = "id", qualifiedByName = "toObjectId")
    RoundDocument toDocument(Round round);

    @Mapping(target = "id", qualifiedByName = "toId")
    Round toRound(RoundDocument document);


    RoundResponse toResponse(Round round);

    @Mapping(target = "hasNext", expression = "java(page.hasNext())")
    @Mapping(target = "content", qualifiedByName = "toContent")
    PageResponse<RoundResponse> toPage(PageImpl<Round> page);

    @Named("toContent")
    default List<RoundResponse> toContent(List<Round> list) {
        return list.stream().map(this::toResponse).toList();
    }
}
