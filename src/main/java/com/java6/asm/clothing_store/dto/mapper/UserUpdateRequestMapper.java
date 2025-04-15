package com.java6.asm.clothing_store.dto.mapper;

import com.java6.asm.clothing_store.dto.request.UserUpdateRequest;
import com.java6.asm.clothing_store.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserUpdateRequestMapper {

    @Mapping(target = "image", ignore = true)
    UserUpdateRequest toResponse(User user);

    @Mapping(target = "image", ignore = true)
    User toEntity(UserUpdateRequest response);
}
