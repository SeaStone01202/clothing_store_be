package com.java6.asm.clothing_store.service.implement;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.java6.asm.clothing_store.constance.RoleEnum;
import com.java6.asm.clothing_store.constance.StatusEnum;
import com.java6.asm.clothing_store.constance.TypeAccountEnum;
import com.java6.asm.clothing_store.dto.mapper.UserResponseMapper;
import com.java6.asm.clothing_store.dto.request.UserRegisterRequest;
import com.java6.asm.clothing_store.dto.request.UserUpdateRequest;
import com.java6.asm.clothing_store.dto.response.UserResponse;
import com.java6.asm.clothing_store.entity.User;
import com.java6.asm.clothing_store.exception.AppException;
import com.java6.asm.clothing_store.exception.ErrorCode;
import com.java6.asm.clothing_store.repository.UserRepository;
import com.java6.asm.clothing_store.service.UserService;
import com.java6.asm.clothing_store.utils.EmailUtil;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserResponseMapper userResponseMapper;

    private final Cloudinary cloudinary;

    private final EmailUtil emailUtil;

    @Transactional
    @Override
    public UserResponse createUser(UserRegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User newUser = User.builder()
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .fullname(request.getFullname())
                .role(RoleEnum.CUSTOMER)
                .status(StatusEnum.ACTIVE)
                .type(TypeAccountEnum.SYSTEM)
                .createdAt(LocalDate.now())
                .build();

        return userResponseMapper.toResponse(userRepository.save(newUser));
    }

    @Transactional
    @PreAuthorize("hasAnyRole('CUSTOMER', 'STAFF', 'DIRECTOR')")
    @Override
    public UserResponse updateUser(UserUpdateRequest request) {

        User user = userRepository.findByEmailAndStatus(request.getEmail(), StatusEnum.ACTIVE)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (request.getFullname() != null && !request.getFullname().isEmpty()) {
            user.setFullname(request.getFullname().trim());
        }

        if (request.getPhone() != null && !request.getPhone().isEmpty()) {
            user.setPhone(request.getPhone().trim());
        }

        if (request.getImage() != null && !request.getImage().isEmpty()) {
            try {
                Map uploadResult = cloudinary.uploader().upload(request.getImage().getBytes(), ObjectUtils.emptyMap());
                String imageUrl = (String) uploadResult.get("secure_url");
                user.setImage(imageUrl);
            } catch (IOException e) {
                throw new AppException(ErrorCode.UnableUploadImageCloudinary);
            }
        }

        User updatedUser = userRepository.save(user);

        return userResponseMapper.toResponse(updatedUser);
    }

    @Transactional
    @PreAuthorize("hasRole('DIRECTOR')")
    @Override
    public boolean deleteUser(String emailUserChangeRole, StatusEnum status) {

        User userNeedChangeRole = userRepository.findByEmail(emailUserChangeRole)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userNeedChangeRole.setStatus(status);

        userRepository.save(userNeedChangeRole);

        return true;
    }

    @Transactional
    @PreAuthorize("hasRole('DIRECTOR')")
    @Override
    public boolean updateRole(String emailUserChangeRole, RoleEnum role) {

        User userNeedChangeRole = userRepository.findByEmail(emailUserChangeRole)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userNeedChangeRole.setRole(role);

        userRepository.save(userNeedChangeRole);

        return true;
    }

    @PreAuthorize("hasRole('DIRECTOR')")
    @Override
    public List<UserResponse> retrieveAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userResponseMapper::toResponse)
                .toList();
    }

    @Transactional
    @Override
    public boolean forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        String newPassword = UUID.randomUUID().toString().replace("-", "").substring(0, 10);

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        try {
            emailUtil.sendOtpEmail(email, newPassword);
        } catch (MessagingException e) {
            throw new AppException(ErrorCode.EMAIL_SEND_FAILED);
        }

        return true;
    }


}
