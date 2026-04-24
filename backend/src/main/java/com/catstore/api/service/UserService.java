package com.catstore.api.service;

import com.catstore.api.dto.RegisterRequest;
import com.catstore.api.vo.LoginVO;

public interface UserService {

    void sendVerificationCode(String phone, String type);

    LoginVO register(RegisterRequest request);
}
