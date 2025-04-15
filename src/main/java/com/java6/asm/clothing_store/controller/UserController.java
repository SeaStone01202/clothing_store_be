package com.java6.asm.clothing_store.controller;

import com.java6.asm.clothing_store.dto.ApiResponse;
import com.java6.asm.clothing_store.dto.request.ChangeRoleUserRequest;
import com.java6.asm.clothing_store.dto.request.FortgotPasswordUserRequest;
import com.java6.asm.clothing_store.dto.request.UserRegisterRequest;
import com.java6.asm.clothing_store.dto.request.UserUpdateRequest;
import com.java6.asm.clothing_store.dto.response.UserResponse;
import com.java6.asm.clothing_store.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.success(userService.retrieveAllUsers()));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(@Valid @RequestBody UserRegisterRequest userRequest) {
        return ResponseEntity.ok(ApiResponse.success(userService.createUser(userRequest)));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@ModelAttribute UserUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userService.updateUser(request)));
    }

    @PutMapping("/status")
    public ResponseEntity<ApiResponse<Boolean>> changeStatusUser(@RequestBody ChangeRoleUserRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userService.deleteUser(request.getEmailUserChangeRole(), request.getStatus())));
    }

    @PutMapping("/role")
    public ResponseEntity<ApiResponse<Boolean>> changeRoleUser(@RequestBody ChangeRoleUserRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userService.updateRole(request.getEmailUserChangeRole(), request.getRole())));
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<ApiResponse<Boolean>> forgotPassword(@RequestBody FortgotPasswordUserRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userService.forgotPassword(request.getEmail())));
    }
}
