package com.java6.asm.clothing_store.dto.mapper;

import com.java6.asm.clothing_store.dto.response.SystemUserResponse;
import com.java6.asm.clothing_store.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SystemUserResponseMapper {

    SystemUserResponse toResponse(User user);
}
