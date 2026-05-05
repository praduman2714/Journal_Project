package com.example.journal_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.journal_app.entity.JournalEntry;
import com.example.journal_app.repository.JournalEntryInterface;

@Service
@Component
public class JournalService {

    @Autowired
    private JournalEntryInterface journalEntryInterface;

    public void saveJournalEntry(JournalEntry journalEntry) {
        journalEntryInterface.save(journalEntry);
    }

    public List<JournalEntry> getAllJournalEntries() {
        return journalEntryInterface.findAll();
    }

    public JournalEntry getJournalEntryById(String id) {
        return journalEntryInterface.findById(id).orElse(null);
    }

    public void deleteJournalEntryById(String id) {
        journalEntryInterface.deleteById(id);
    }
}
