package com.java6.asm.clothing_store.dto.mapper;

import com.java6.asm.clothing_store.dto.response.AddressResponse;
import com.java6.asm.clothing_store.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressResponseMapper {

    @Mapping(source = "user.email", target = "email")
    AddressResponse toDto(Address address);

    Address toEntity(AddressResponse addressResponse);
}