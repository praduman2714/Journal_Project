package com.example.journal_app.controller;

import com.example.journal_app.entity.JournalEntry;
import com.example.journal_app.service.JournalService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalService journalService;

    @GetMapping("")
    public ResponseEntity<List<JournalEntry>> getAll() {
        return new ResponseEntity<>(journalService.getAllJournalEntries(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<JournalEntry> saveJournalEntry(@RequestBody JournalEntry journalEntry) {
        journalService.saveJournalEntry(journalEntry);
        return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<JournalEntry> getJournalEntry(@PathVariable String id) {
        JournalEntry journalEntry = journalService.getJournalEntryById(id);
        if (journalEntry != null) {
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable String id) {
        journalService.deleteJournalEntryById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<JournalEntry> updateJournalEntry(@PathVariable String id,
            @RequestBody JournalEntry newEntry) {
        JournalEntry oldEntry = journalService.getJournalEntryById(id);
        if (oldEntry != null) {
            if (newEntry.getTitle() != null && !newEntry.getTitle().equals("")) {
                oldEntry.setTitle(newEntry.getTitle());
            }
            if (newEntry.getContent() != null && !newEntry.getContent().equals("")) {
                oldEntry.setContent(newEntry.getContent());
            }
            journalService.saveJournalEntry(oldEntry);
            return ResponseEntity.ok(oldEntry);
        }
        return ResponseEntity.notFound().build();
    }
}
