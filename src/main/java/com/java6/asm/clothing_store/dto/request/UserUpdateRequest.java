package com.java6.asm.clothing_store.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class UserUpdateRequest {

    private String email;

    private String fullname;

    private MultipartFile image;

    private String phone;
}
