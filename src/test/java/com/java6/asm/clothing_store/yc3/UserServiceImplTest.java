package com.java6.asm.clothing_store.yc3;

import com.java6.asm.clothing_store.constance.RoleEnum;
import com.java6.asm.clothing_store.constance.StatusEnum;
import com.java6.asm.clothing_store.constance.TypeAccountEnum;
import com.java6.asm.clothing_store.dto.mapper.UserResponseMapper;
import com.java6.asm.clothing_store.dto.request.UserRegisterRequest;
import com.java6.asm.clothing_store.dto.response.UserResponse;
import com.java6.asm.clothing_store.entity.User;
import com.java6.asm.clothing_store.exception.AppException;
import com.java6.asm.clothing_store.exception.ErrorCode;
import com.java6.asm.clothing_store.repository.UserRepository;
import com.java6.asm.clothing_store.service.implement.UserServiceImpl;
import com.java6.asm.clothing_store.utils.EmailUtil;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserResponseMapper userResponseMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private EmailUtil emailUtil;


    private UserRegisterRequest request;

    @BeforeEach
    void setUp() {
        request = UserRegisterRequest.builder()
                .email("newuser@example.com")
                .password("raw_password")
                .fullname("John Doe")
                .build();
    }

    @Test
    void testForgotPassword_Success_TC08() throws MessagingException {
        User user = buildDefaultUser();
        when(userRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        boolean result = userService.forgotPassword("existing@example.com");

        assertTrue(result);
        verify(userRepository).save(any(User.class));
        verify(emailUtil).sendOtpEmail(eq("existing@example.com"), any());
    }

    @Test
    void testForgotPassword_UserNotFound_TC09() {
        when(userRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        AppException ex = assertThrows(AppException.class,
                () -> userService.forgotPassword("notfound@example.com"));

        assertEquals(ErrorCode.USER_NOT_EXISTED, ex.getErrorCode());
    }

    @Test
    void testForgotPassword_EncodePasswordCalled_TC10() {
        User user = buildDefaultUser();
        when(userRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        userService.forgotPassword("existing@example.com");

        verify(passwordEncoder).encode(any());
    }

    @Test
    void testForgotPassword_PasswordLengthIs10_TC11() throws MessagingException {
        User user = buildDefaultUser();
        when(userRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        doNothing().when(emailUtil).sendOtpEmail(eq("existing@example.com"), passwordCaptor.capture());

        userService.forgotPassword("existing@example.com");

        String rawPasswordSent = passwordCaptor.getValue();
        assertEquals(10, rawPasswordSent.length());
    }

    @Test
    void testForgotPassword_SaveNewPasswordToUser_TC12() {
        User user = buildDefaultUser();
        when(userRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        userService.forgotPassword("existing@example.com");

        assertEquals("encodedPassword", user.getPassword());
    }

    @Test
    void testForgotPassword_ThrowsExceptionWhenEmailFail_TC13() throws MessagingException {
        User user = buildDefaultUser();
        when(userRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        doThrow(new MessagingException("Email failed"))
                .when(emailUtil).sendOtpEmail(any(), any());

        AppException ex = assertThrows(AppException.class,
                () -> userService.forgotPassword("existing@example.com"));

        assertEquals(ErrorCode.EMAIL_SEND_FAILED, ex.getErrorCode());
    }

    @Test
    void testForgotPassword_SaveMethodCalledOnce_TC14() {
        User user = buildDefaultUser();
        when(userRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        userService.forgotPassword("existing@example.com");

        verify(userRepository).save(user);
    }

    @Test
    void testForgotPassword_sendOtpEmailContentMatch_TC15() throws MessagingException {
        User user = buildDefaultUser();
        when(userRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        doNothing().when(emailUtil).sendOtpEmail(emailCaptor.capture(), passwordCaptor.capture());

        userService.forgotPassword("existing@example.com");

        assertEquals("existing@example.com", emailCaptor.getValue());
        assertEquals(10, passwordCaptor.getValue().length());
    }


    @Test
    void testCreateUser_Success_TC16() {
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(userResponseMapper.toResponse(any())).thenReturn(buildDefaultUserResponse());

        UserResponse response = userService.createUser(request);

        assertNotNull(response);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testCreateUser_EmailExisted_ThrowsException_TC17() {
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        AppException ex = assertThrows(AppException.class, () -> userService.createUser(request));
        assertEquals(ErrorCode.USER_EXISTED, ex.getErrorCode());
    }

    @Test
    void testCreateUser_EncodePasswordCalled_TC18() {
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode("raw_password")).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(userResponseMapper.toResponse(any())).thenReturn(buildDefaultUserResponse());

        userService.createUser(request);

        verify(passwordEncoder).encode("raw_password");
    }

    @Test
    void testCreateUser_DefaultRoleIsCustomer_TC19() {
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encoded_password");
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(userCaptor.capture())).thenAnswer(invocation -> invocation.getArgument(0));
        when(userResponseMapper.toResponse(any())).thenReturn(buildDefaultUserResponse());

        userService.createUser(request);

        assertEquals(RoleEnum.CUSTOMER, userCaptor.getValue().getRole());
    }

    @Test
    void testCreateUser_DefaultStatusIsActive_20() {
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encoded_password");
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(userCaptor.capture())).thenAnswer(invocation -> invocation.getArgument(0));
        when(userResponseMapper.toResponse(any())).thenReturn(buildDefaultUserResponse());

        userService.createUser(request);

        assertEquals(StatusEnum.ACTIVE, userCaptor.getValue().getStatus());
    }

    @Test
    void testCreateUser_DefaultTypeIsSystem_TC21() {
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encoded_password");
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(userCaptor.capture())).thenAnswer(invocation -> invocation.getArgument(0));
        when(userResponseMapper.toResponse(any())).thenReturn(buildDefaultUserResponse());

        userService.createUser(request);

        assertEquals(TypeAccountEnum.SYSTEM, userCaptor.getValue().getType());
    }

    @Test
    void testCreateUser_FullnameWithSpaces_TC22() {
        request.setFullname("  John Doe  ");
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encoded_password");
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(userCaptor.capture())).thenAnswer(invocation -> invocation.getArgument(0));
        when(userResponseMapper.toResponse(any())).thenReturn(buildDefaultUserResponse());

        userService.createUser(request);

        assertEquals("  John Doe  ", userCaptor.getValue().getFullname());
    }

    @Test
    void testCreateUser_CreatedAtToday_TC23() {
        LocalDate today = LocalDate.now();
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encoded_password");
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(userCaptor.capture())).thenAnswer(invocation -> invocation.getArgument(0));
        when(userResponseMapper.toResponse(any())).thenReturn(buildDefaultUserResponse());

        userService.createUser(request);

        assertEquals(today, userCaptor.getValue().getCreatedAt());
    }

    private UserResponse buildDefaultUserResponse() {
        return UserResponse.builder()
                .email("newuser@example.com")
                .fullname("John Doe")
                .image(null)
                .role(RoleEnum.CUSTOMER)
                .phone(null)
                .type(TypeAccountEnum.SYSTEM)
                .status(StatusEnum.ACTIVE)
                .build();
    }

    private User buildDefaultUser() {
        return User.builder()
                .email("existing@example.com")
                .password("oldPassword")
                .status(StatusEnum.ACTIVE)
                .type(TypeAccountEnum.SYSTEM)
                .role(RoleEnum.CUSTOMER)
                .fullname("Test User")
                .createdAt(LocalDate.now())
                .build();
    }

}
