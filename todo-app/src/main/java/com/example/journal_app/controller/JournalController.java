package com.example.journal_app.controller;

import com.example.journal_app.entity.JournalEntry;
import com.example.journal_app.service.JournalService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalService journalService;

    private Map<Long, JournalEntry> journalEntryMap = new HashMap<>();

    @GetMapping("")
    public List<JournalEntry> getAll() {
        return new ArrayList<>(journalEntryMap.values());
    }

    @PostMapping()
    public JournalEntry saveJournalEntry(@RequestBody JournalEntry journalEntry) {
        journalEntryMap.put(journalEntry.getId(), journalEntry);
        return journalEntry;
    }

}
