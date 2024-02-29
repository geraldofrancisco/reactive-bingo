package com.geraldo.reactivebingo.domain.mapper;

import com.geraldo.reactivebingo.domain.model.document.player.PlayerDocument;
import com.geraldo.reactivebingo.domain.model.dto.Player;
import com.geraldo.reactivebingo.domain.model.request.player.PlayerUpdateRequest;
import com.geraldo.reactivebingo.domain.model.response.PageResponse;
import com.geraldo.reactivebingo.domain.model.response.player.PlayerResponse;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface PlayerMapper {
    Player toPlayer(String nickname);

    @Mapping(target = "id", qualifiedByName = "toObjectId")
    PlayerDocument toDocument(Player player);

    @Mapping(target = "id", qualifiedByName = "toId")
    Player toPlayer(PlayerDocument document);

    Player toPlayer(PlayerUpdateRequest request);

    PlayerResponse toResponse(Player player);


    @Mapping(target = "hasNext", expression = "java(page.hasNext())")
    @Mapping(target = "content", qualifiedByName = "toContent")
    PageResponse<PlayerResponse> toPage(PageImpl<Player> page);

    @Named("toContent")
    default List<PlayerResponse> toContent(List<Player> list) {
        return list.stream().map(this::toResponse).toList();
    }

    @Named("toObjectId")
    default ObjectId toObjectId(String id) {
        if(StringUtils.isEmpty(id))
            return null;
        return new ObjectId(id);
    }

    @Named("toId")
    default String toId(ObjectId id) {
        return id.toHexString();
    }
}
