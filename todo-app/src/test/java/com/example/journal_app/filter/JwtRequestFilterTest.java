package com.example.journal_app.filter;

import com.example.journal_app.service.UserDetailsServiceImpl;
import com.example.journal_app.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class JwtRequestFilterTest {

    private final JwtUtil jwtUtil = new JwtUtil("journal-app-development-secret-key-change-this-value");
    private final JwtRequestFilter jwtRequestFilter = new JwtRequestFilter();

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldAuthenticateRequestWhenJwtIsValid() throws ServletException, IOException {
        ReflectionTestUtils.setField(jwtRequestFilter, "jwtUtil", jwtUtil);
        ReflectionTestUtils.setField(jwtRequestFilter, "userDetailsService", new StubUserDetailsService());

        String token = jwtUtil.generateToken("praduman");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);
        MockHttpServletResponse response = new MockHttpServletResponse();
        CountingFilterChain filterChain = new CountingFilterChain();

        jwtRequestFilter.doFilter(request, response, filterChain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals("praduman", authentication.getName());
        assertEquals(1, filterChain.callCount);
    }

    @Test
    void shouldContinueWithoutAuthenticationWhenJwtIsMissing() throws ServletException, IOException {
        ReflectionTestUtils.setField(jwtRequestFilter, "jwtUtil", jwtUtil);
        ReflectionTestUtils.setField(jwtRequestFilter, "userDetailsService", new StubUserDetailsService());

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        CountingFilterChain filterChain = new CountingFilterChain();

        jwtRequestFilter.doFilter(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(1, filterChain.callCount);
    }

    @Test
    void shouldReturnUnauthorizedWhenJwtIsInvalid() throws ServletException, IOException {
        ReflectionTestUtils.setField(jwtRequestFilter, "jwtUtil", jwtUtil);
        ReflectionTestUtils.setField(jwtRequestFilter, "userDetailsService", new StubUserDetailsService());

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer invalid-token");
        MockHttpServletResponse response = new MockHttpServletResponse();
        CountingFilterChain filterChain = new CountingFilterChain();

        jwtRequestFilter.doFilter(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(401, response.getStatus());
        assertEquals(0, filterChain.callCount);
    }

    private static class StubUserDetailsService extends UserDetailsServiceImpl {
        @Override
        public UserDetails loadUserByUsername(String username) {
            return new User(username, "encoded-password", Collections.emptyList());
        }
    }

    private static class CountingFilterChain implements FilterChain {
        private int callCount;

        @Override
        public void doFilter(ServletRequest request, ServletResponse response) {
            callCount++;
        }
    }
}
