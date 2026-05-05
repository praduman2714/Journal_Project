package com.example.journal_app.controller;

import com.example.journal_app.entity.JournalEntry;
import com.example.journal_app.service.JournalService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<JournalEntry> getAll() {
        return journalService.getAllJournalEntries();
    }

    @PostMapping()
    public JournalEntry saveJournalEntry(@RequestBody JournalEntry journalEntry) {
        journalService.saveJournalEntry(journalEntry);
        return journalEntry;

    }

    @GetMapping("{id}")
    public JournalEntry getJournalEntry(@PathVariable String id) {
        return journalService.getJournalEntryById(id);
    }

    @DeleteMapping("{id}")
    public boolean deleteJournalEntry(@PathVariable String id) {
        journalService.deleteJournalEntryById(id);
        return true;
    }

    @PutMapping("{id}")
    public JournalEntry updateJournalEntry(@PathVariable String id, @RequestBody JournalEntry newEntry) {
        JournalEntry oldEntry = journalService.getJournalEntryById(id);
        if (oldEntry != null) {
            oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle()
                    : oldEntry.getTitle());
            oldEntry.setContent(
                    newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent()
                            : oldEntry.getContent());
            journalService.saveJournalEntry(oldEntry);
            return oldEntry;
        }
        return null;
    }
}
