package com.catstore.api.util;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtUtil, "secret",
                "catstore-secret-key-for-jwt-token-generation-must-be-at-least-256-bits-long");
        ReflectionTestUtils.setField(jwtUtil, "accessTokenExpiration", 1800000L);
        ReflectionTestUtils.setField(jwtUtil, "refreshTokenExpiration", 604800000L);
    }

    @Test
    void generateAccessToken_Success() {
        String token = jwtUtil.generateAccessToken(1L, "13800138000");

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void generateRefreshToken_Success() {
        String token = jwtUtil.generateRefreshToken(1L, "13800138000");

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void parseToken_Success() {
        String token = jwtUtil.generateAccessToken(1L, "13800138000");

        Claims claims = jwtUtil.parseToken(token);

        assertNotNull(claims);
        assertEquals("1", claims.getSubject());
        assertEquals("13800138000", claims.get("phoneNumber"));
    }

    @Test
    void getUserIdFromToken_Success() {
        String token = jwtUtil.generateAccessToken(123L, "13800138000");

        Long userId = jwtUtil.getUserIdFromToken(token);

        assertEquals(123L, userId);
    }

    @Test
    void isTokenExpired_FalseForValidToken() {
        String token = jwtUtil.generateAccessToken(1L, "13800138000");

        assertFalse(jwtUtil.isTokenExpired(token));
    }

    @Test
    void isTokenExpired_TrueForInvalidToken() {
        assertTrue(jwtUtil.isTokenExpired("invalid.token.here"));
    }

    @Test
    void validateToken_TrueForValidToken() {
        String token = jwtUtil.generateAccessToken(1L, "13800138000");

        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    void validateToken_FalseForInvalidToken() {
        assertFalse(jwtUtil.validateToken("invalid.token.here"));
    }

    @Test
    void validateToken_FalseForEmptyToken() {
        assertFalse(jwtUtil.validateToken(""));
    }
}
