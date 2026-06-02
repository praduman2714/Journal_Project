package com.example.journal_app.service;

import com.example.journal_app.entity.UserEntry;
import com.example.journal_app.repository.UserEntryInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserEntryInterface userEntryInterface;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void shouldLoadUserByUsername() {
        UserEntry userEntry = UserEntry.builder()
                .userName("praduman")
                .password("encoded-password")
                .build();

        when(userEntryInterface.findByUserName("praduman")).thenReturn(userEntry);

        UserDetails userDetails = userDetailsService.loadUserByUsername("praduman");

        assertEquals("praduman", userDetails.getUsername());
        assertEquals("encoded-password", userDetails.getPassword());
    }

    @Test
    void shouldThrowWhenUserDoesNotExist() {
        when(userEntryInterface.findByUserName("missing-user")).thenReturn(null);

        assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("missing-user")
        );
    }
}
