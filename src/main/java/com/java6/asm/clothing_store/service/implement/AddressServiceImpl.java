package com.java6.asm.clothing_store.service.implement;

import com.java6.asm.clothing_store.constance.StatusEnum;
import com.java6.asm.clothing_store.dto.mapper.AddressResponseMapper;
import com.java6.asm.clothing_store.dto.request.AddressRequest;
import com.java6.asm.clothing_store.dto.response.AddressResponse;
import com.java6.asm.clothing_store.entity.Address;
import com.java6.asm.clothing_store.entity.User;
import com.java6.asm.clothing_store.exception.AppException;
import com.java6.asm.clothing_store.exception.ErrorCode;
import com.java6.asm.clothing_store.repository.AddressRepository;
import com.java6.asm.clothing_store.repository.UserRepository;
import com.java6.asm.clothing_store.service.AddressService;
import com.java6.asm.clothing_store.service.authentication.AccessTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    private final AddressResponseMapper addressResponseMapper;

    private final AccessTokenService accessTokenService;

    @Transactional
    @Override
    public AddressResponse createAddress(AddressRequest request) {

        User user = userRepository.findByEmailAndStatus(request.getEmail(), StatusEnum.ACTIVE)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Address address = new Address();
        address.setAddressLine(request.getAddressLine());
        address.setWard(request.getWard());
        address.setDistrict(request.getDistrict());
        address.setCity(request.getCity());
        address.setIsDefault(request.getIsDefault() != null ? request.getIsDefault() : false);
        address.setUser(user);

        Address savedAddress = addressRepository.save(address);

        return addressResponseMapper.toDto(savedAddress);
    }

    @Override
    public List<AddressResponse> getAllAddresses(String accessToken) {

        if (accessToken == null || accessToken.isBlank()) {
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }

        String email = accessTokenService.validateToken(accessToken);

        return addressRepository.findAllByUserEmail(email)
                .stream()
                .map(addressResponseMapper::toDto)
                .toList();
    }
}