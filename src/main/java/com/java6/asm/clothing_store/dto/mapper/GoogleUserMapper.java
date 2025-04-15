package com.java6.asm.clothing_store.dto.mapper;

import com.java6.asm.clothing_store.dto.response.GoogleUserRegisterResponse;
import com.java6.asm.clothing_store.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GoogleUserMapper {

    GoogleUserRegisterResponse toResponse(User user);

    User toEntity(GoogleUserRegisterResponse request);
}
