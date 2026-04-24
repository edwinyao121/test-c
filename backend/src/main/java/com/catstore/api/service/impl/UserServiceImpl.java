package com.catstore.api.service.impl;

import com.catstore.api.dto.RegisterRequest;
import com.catstore.api.entity.User;
import com.catstore.api.entity.VerificationCode;
import com.catstore.api.exception.BusinessException;
import com.catstore.api.repository.UserRepository;
import com.catstore.api.repository.VerificationCodeRepository;
import com.catstore.api.service.UserService;
import com.catstore.api.util.JwtUtil;
import com.catstore.api.vo.LoginVO;
import com.catstore.api.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    private static final String CODE_TYPE_REGISTER = "REGISTER";
    private static final int CODE_VALID_MINUTES = 5;
    private static final String DEFAULT_NICKNAME = "用户";

    @Override
    @Transactional
    public void sendVerificationCode(String phone, String type) {
        verificationCodeRepository.deleteExpiredCodes(LocalDateTime.now());

        verificationCodeRepository.markAsUsed(phone, type, LocalDateTime.now());

        String code = generateRandomCode(6);

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setPhoneNumber(phone);
        verificationCode.setCode(code);
        verificationCode.setType(type);
        verificationCode.setExpiresTime(LocalDateTime.now().plusMinutes(CODE_VALID_MINUTES));
        verificationCode.setUsed(0);

        verificationCodeRepository.save(verificationCode);

        System.out.println("【模拟发送验证码】手机号: " + phone + ", 验证码: " + code);
    }

    @Override
    @Transactional
    public LoginVO register(RegisterRequest request) {
        if (userRepository.existsByPhoneNumber(request.getPhone())) {
            throw new BusinessException(1003, "手机号已注册");
        }

        VerificationCode validCode = verificationCodeRepository
                .findLatestValidCode(request.getPhone(), CODE_TYPE_REGISTER)
                .orElseThrow(() -> new BusinessException(1002, "验证码错误或已过期"));

        if (!validCode.getCode().equals(request.getCode())) {
            throw new BusinessException(1002, "验证码错误");
        }

        if (validCode.getExpiresTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException(1002, "验证码已过期");
        }

        User user = new User();
        user.setPhoneNumber(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNickname(DEFAULT_NICKNAME + System.currentTimeMillis() % 10000);
        user.setStatus(1);

        user = userRepository.save(user);

        verificationCodeRepository.markAsUsed(request.getPhone(), CODE_TYPE_REGISTER, LocalDateTime.now());

        String token = jwtUtil.generateAccessToken(user.getId(), user.getPhoneNumber());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getPhoneNumber());

        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setRefreshToken(refreshToken);
        loginVO.setUser(convertToUserVO(user));

        return loginVO;
    }

    private String generateRandomCode(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    private UserVO convertToUserVO(User user) {
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setPhoneNumber(user.getPhoneNumber());
        vo.setNickname(user.getNickname());
        vo.setAvatarUrl(user.getAvatarUrl());
        vo.setCreateTime(user.getCreateTime());
        return vo;
    }
}
