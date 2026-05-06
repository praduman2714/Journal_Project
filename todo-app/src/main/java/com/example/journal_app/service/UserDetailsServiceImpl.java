package com.example.journal_app.service;

import com.example.journal_app.entity.UserEntry;
import com.example.journal_app.repository.UserEntryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserEntryInterface userEntryInterface;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntry userEntry = userEntryInterface.findByUserName(username);
        if (userEntry == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(userEntry.getUserName(), userEntry.getPassword(), new ArrayList<>());
    }
}
