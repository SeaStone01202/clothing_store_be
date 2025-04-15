package com.java6.asm.clothing_store.dto.mapper;

import com.java6.asm.clothing_store.dto.request.UserRequest;
import com.java6.asm.clothing_store.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {

    UserRequest toResponse(User user);

    User toEntity(UserRequest request);
}
