package com.catstore.api.service;

import com.catstore.api.dto.RegisterRequest;
import com.catstore.api.entity.User;
import com.catstore.api.entity.VerificationCode;
import com.catstore.api.exception.BusinessException;
import com.catstore.api.repository.UserRepository;
import com.catstore.api.repository.VerificationCodeRepository;
import com.catstore.api.service.impl.UserServiceImpl;
import com.catstore.api.util.JwtUtil;
import com.catstore.api.vo.LoginVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private VerificationCodeRepository verificationCodeRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private VerificationCode testCode;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setPhoneNumber("13800138000");
        testUser.setPassword("encodedPassword");
        testUser.setNickname("测试用户");
        testUser.setStatus(1);

        testCode = new VerificationCode();
        testCode.setId(1L);
        testCode.setPhoneNumber("13800138000");
        testCode.setCode("123456");
        testCode.setType("REGISTER");
        testCode.setExpiresTime(LocalDateTime.now().plusMinutes(5));
        testCode.setUsed(0);
    }

    @Test
    void sendVerificationCode_Success() {
        doNothing().when(verificationCodeRepository).deleteExpiredCodes(any());
        when(verificationCodeRepository.markAsUsed(anyString(), anyString(), any())).thenReturn(0);
        when(verificationCodeRepository.save(any())).thenReturn(testCode);

        userService.sendVerificationCode("13800138000", "REGISTER");

        verify(verificationCodeRepository).deleteExpiredCodes(any());
        verify(verificationCodeRepository).markAsUsed(eq("13800138000"), eq("REGISTER"), any());
        verify(verificationCodeRepository).save(any(VerificationCode.class));
    }

    @Test
    void register_Success() {
        RegisterRequest request = new RegisterRequest();
        request.setPhone("13800138000");
        request.setCode("123456");
        request.setPassword("password123");

        when(userRepository.existsByPhoneNumber("13800138000")).thenReturn(false);
        when(verificationCodeRepository.findLatestValidCode("13800138000", "REGISTER"))
                .thenReturn(Optional.of(testCode));
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(jwtUtil.generateAccessToken(anyLong(), anyString())).thenReturn("accessToken");
        when(jwtUtil.generateRefreshToken(anyLong(), anyString())).thenReturn("refreshToken");

        LoginVO result = userService.register(request);

        assertNotNull(result);
        assertEquals("accessToken", result.getToken());
        assertEquals("refreshToken", result.getRefreshToken());
        assertNotNull(result.getUser());
        assertEquals("13800138000", result.getUser().getPhoneNumber());

        verify(userRepository).existsByPhoneNumber("13800138000");
        verify(verificationCodeRepository).findLatestValidCode("13800138000", "REGISTER");
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_PhoneAlreadyExists() {
        RegisterRequest request = new RegisterRequest();
        request.setPhone("13800138000");
        request.setCode("123456");
        request.setPassword("password123");

        when(userRepository.existsByPhoneNumber("13800138000")).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> userService.register(request));

        assertEquals(1003, exception.getCode());
        assertEquals("手机号已注册", exception.getMessage());

        verify(userRepository).existsByPhoneNumber("13800138000");
        verify(userRepository, never()).save(any());
    }

    @Test
    void register_InvalidCode() {
        RegisterRequest request = new RegisterRequest();
        request.setPhone("13800138000");
        request.setCode("wrongCode");
        request.setPassword("password123");

        when(userRepository.existsByPhoneNumber("13800138000")).thenReturn(false);
        when(verificationCodeRepository.findLatestValidCode("13800138000", "REGISTER"))
                .thenReturn(Optional.of(testCode));

        BusinessException exception = assertThrows(BusinessException.class,
                () -> userService.register(request));

        assertEquals(1002, exception.getCode());
        assertEquals("验证码错误", exception.getMessage());
    }

    @Test
    void register_ExpiredCode() {
        RegisterRequest request = new RegisterRequest();
        request.setPhone("13800138000");
        request.setCode("123456");
        request.setPassword("password123");

        testCode.setExpiresTime(LocalDateTime.now().minusMinutes(1));

        when(userRepository.existsByPhoneNumber("13800138000")).thenReturn(false);
        when(verificationCodeRepository.findLatestValidCode("13800138000", "REGISTER"))
                .thenReturn(Optional.of(testCode));

        BusinessException exception = assertThrows(BusinessException.class,
                () -> userService.register(request));

        assertEquals(1002, exception.getCode());
        assertEquals("验证码已过期", exception.getMessage());
    }
}
