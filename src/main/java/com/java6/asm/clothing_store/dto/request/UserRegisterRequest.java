package com.java6.asm.clothing_store.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserRegisterRequest {

    @NotBlank(message = "Mật khẩu không được để trống!")
    @Size(min = 5, message = "Password phải từ 5 ký tự trở lên")
    private String password;

    @NotBlank(message = "Email không được để trống!")
    @Email(message = "Email không hợp lệ!")
    private String email;

    @NotBlank(message = "Họ và tên không được để trống!")
    @Size(min = 5, message = "Họ tên phải từ 5 ký tự trở lên")
    private String fullname;
}
