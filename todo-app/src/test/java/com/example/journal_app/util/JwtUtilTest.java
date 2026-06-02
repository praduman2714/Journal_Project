package com.example.journal_app.util;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtUtilTest {

    private final JwtUtil jwtUtil = new JwtUtil("journal-app-development-secret-key-change-this-value");

    @Test
    void shouldGenerateTokenAndExtractUsername() {
        String token = jwtUtil.generateToken("praduman");

        String username = jwtUtil.extractUsername(token);

        assertEquals("praduman", username);
    }

    @Test
    void shouldValidateTokenForSameUser() {
        UserDetails userDetails = new User("praduman", "password", Collections.emptyList());

        String token = jwtUtil.generateToken("praduman");

        assertTrue(jwtUtil.validateToken(token, userDetails));
    }

    @Test
    void shouldRejectTokenForDifferentUser() {
        UserDetails userDetails = new User("another-user", "password", Collections.emptyList());

        String token = jwtUtil.generateToken("praduman");

        assertFalse(jwtUtil.validateToken(token, userDetails));
    }
}
