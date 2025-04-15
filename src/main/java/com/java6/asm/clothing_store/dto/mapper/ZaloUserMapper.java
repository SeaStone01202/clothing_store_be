package com.java6.asm.clothing_store.dto.mapper;

import com.java6.asm.clothing_store.dto.response.GoogleUserRegisterResponse;
import com.java6.asm.clothing_store.dto.response.ZaloUserRegisterResponse;
import com.java6.asm.clothing_store.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ZaloUserMapper {

    ZaloUserRegisterResponse toResponse(User user);

    User toEntity(ZaloUserRegisterResponse request);
}
