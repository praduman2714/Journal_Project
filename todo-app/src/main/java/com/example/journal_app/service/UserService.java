package com.example.journal_app.service;

import com.example.journal_app.entity.UserEntry;
import com.example.journal_app.repository.UserEntryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserEntryInterface userEntryInterface;

    public void saveUser(UserEntry user) {
        userEntryInterface.save(user);
    }

    public List<UserEntry> getAllUsers() {
        return userEntryInterface.findAll();
    }

    public Optional<UserEntry> getUserById(Object id) {
        return userEntryInterface.findById(id);
    }

    public UserEntry getUserByUserName(String userName) {
        return userEntryInterface.findByUserName(userName);
    }

    public void deleteUserById(Object id) {
        userEntryInterface.deleteById(id);
    }
}
