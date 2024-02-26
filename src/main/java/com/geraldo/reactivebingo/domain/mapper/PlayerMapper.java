package com.geraldo.reactivebingo.domain.mapper;

import com.geraldo.reactivebingo.domain.model.document.player.PlayerDocument;
import com.geraldo.reactivebingo.domain.model.dto.PageResponseDTO;
import com.geraldo.reactivebingo.domain.model.dto.Player;
import com.geraldo.reactivebingo.domain.model.response.player.PageResponse;
import com.geraldo.reactivebingo.domain.model.response.player.PlayerCreateResponse;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface PlayerMapper {
    Player toPlayer(String nickname);

    @Mapping(target = "id", qualifiedByName = "toObjectId")
    PlayerDocument toDocument(Player player);

    @Mapping(target = "id", qualifiedByName = "toId")
    Player toPlayer(PlayerDocument document);

    PlayerCreateResponse toResponse(Player player);

    List<PlayerCreateResponse> toResponse(List<Player> player);

    @Mapping(target = "content", qualifiedByName = "toContent")
    PageResponse<PlayerCreateResponse> toPage(PageImpl<Player> page);

    @Named("toContent")
    default List<PlayerCreateResponse> toContent(List<Player> list) {
        if(Objects.isNull(list))
            return null;
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
