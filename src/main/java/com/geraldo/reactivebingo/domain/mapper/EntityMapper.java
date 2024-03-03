package com.geraldo.reactivebingo.domain.mapper;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.mapstruct.Named;

public interface EntityMapper {
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
