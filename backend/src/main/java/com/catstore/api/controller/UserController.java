package com.catstore.api.controller;

import com.catstore.api.dto.RegisterRequest;
import com.catstore.api.dto.SendCodeRequest;
import com.catstore.api.service.UserService;
import com.catstore.api.vo.ApiResponse;
import com.catstore.api.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "用户管理", description = "用户注册、登录等接口")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/send-code")
    @Operation(summary = "发送验证码", description = "向指定手机号发送验证码")
    public ApiResponse<Void> sendCode(@Valid @RequestBody SendCodeRequest request) {
        userService.sendVerificationCode(request.getPhone(), request.getType());
        return ApiResponse.success();
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "使用手机号、验证码、密码注册新用户")
    public ApiResponse<LoginVO> register(@Valid @RequestBody RegisterRequest request) {
        LoginVO loginVO = userService.register(request);
        return ApiResponse.success(loginVO);
    }
}
