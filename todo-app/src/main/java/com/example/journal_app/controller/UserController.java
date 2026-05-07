package com.example.journal_app.controller;

import com.example.journal_app.entity.UserEntry;
import com.example.journal_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserEntry>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody UserEntry userEntry) {
        try {
            userService.saveUser(userEntry);
            return new ResponseEntity<>(userEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody UserEntry userEntry, @PathVariable String userName) {
        try {
            UserEntry userInDb = userService.getUserByUserName(userName);
            if (userInDb != null) {
                userInDb.setUserName(userEntry.getUserName() != null ? userEntry.getUserName() : userInDb.getUserName());
                userInDb.setEmail(userEntry.getEmail() != null ? userEntry.getEmail() : userInDb.getEmail());
                userInDb.setPassword(userEntry.getPassword() != null ? userEntry.getPassword() : userInDb.getPassword());
                userService.saveUser(userInDb);
                return new ResponseEntity<>(userInDb, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable ObjectId id) {
        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
