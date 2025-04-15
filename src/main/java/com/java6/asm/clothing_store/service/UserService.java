package com.java6.asm.clothing_store.service;

import com.java6.asm.clothing_store.constance.RoleEnum;
import com.java6.asm.clothing_store.constance.StatusEnum;
import com.java6.asm.clothing_store.dto.request.UserRegisterRequest;
import com.java6.asm.clothing_store.dto.request.UserRequest;
import com.java6.asm.clothing_store.dto.request.UserUpdateRequest;
import com.java6.asm.clothing_store.dto.response.SystemUserRegisterResponse;
import com.java6.asm.clothing_store.dto.response.UserResponse;

import javax.management.relation.RoleStatus;
import java.util.List;

public interface UserService {

    UserResponse createUser(UserRegisterRequest request);

    UserResponse updateUser(UserUpdateRequest request);

    boolean deleteUser(String emailUserChangeRole, StatusEnum status);

    boolean updateRole(String emailUserChangeRole, RoleEnum role);

    List<UserResponse> retrieveAllUsers();
    boolean forgotPassword(String email);



}
