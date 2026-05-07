package com.example.journal_app.controller;

import com.example.journal_app.entity.JournalEntry;
import com.example.journal_app.service.JournalService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<List<JournalEntry>> getAll(Authentication authentication) {
        String userName = authentication.getName();
        return new ResponseEntity<>(journalService.getJournalEntriesByUserName(userName), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> saveJournalEntry(@RequestBody JournalEntry journalEntry, Authentication authentication) {
        try {
            String userName = authentication.getName();
            JournalEntry savedEntry = journalService.saveJournalEntry(journalEntry, userName);
            return new ResponseEntity<>(savedEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<JournalEntry> getJournalEntry(@PathVariable String id, Authentication authentication) {
        String userName = authentication.getName();
        JournalEntry journalEntry = journalService.getJournalEntryById(id, userName);
        if (journalEntry != null) {
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable String id, Authentication authentication) {
        try {
            String userName = authentication.getName();
            boolean deleted = journalService.deleteJournalEntryById(id, userName);
            if (!deleted) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable String id,
            @RequestBody JournalEntry newEntry, Authentication authentication) {
        try {
            String userName = authentication.getName();
            JournalEntry oldEntry = journalService.getJournalEntryById(id, userName);
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
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
