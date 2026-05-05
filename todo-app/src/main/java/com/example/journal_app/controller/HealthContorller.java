package com.example.journal_app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthContorller {
    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
