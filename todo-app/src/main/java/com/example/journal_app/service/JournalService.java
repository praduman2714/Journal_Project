package com.example.journal_app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.journal_app.entity.JournalEntry;
import com.example.journal_app.entity.UserEntry;
import com.example.journal_app.repository.JournalEntryInterface;
import com.example.journal_app.repository.UserEntryInterface;

@Service
public class JournalService {

    @Autowired
    private JournalEntryInterface journalEntryInterface;

    @Autowired
    private UserEntryInterface userEntryInterface;

    public void saveJournalEntry(JournalEntry journalEntry) {
        journalEntryInterface.save(journalEntry);
    }

    public JournalEntry saveJournalEntry(JournalEntry journalEntry, String userName) {
        UserEntry user = getUserOrThrow(userName);
        JournalEntry savedEntry = journalEntryInterface.save(journalEntry);

        if (user.getJournalEntries() == null) {
            user.setJournalEntries(new ArrayList<>());
        }
        user.getJournalEntries().add(savedEntry);
        userEntryInterface.save(user);

        return savedEntry;
    }

    public List<JournalEntry> getAllJournalEntries() {
        return journalEntryInterface.findAll();
    }

    public List<JournalEntry> getJournalEntriesByUserName(String userName) {
        UserEntry user = getUserOrThrow(userName);
        return user.getJournalEntries() != null ? user.getJournalEntries() : new ArrayList<>();
    }

    public JournalEntry getJournalEntryById(String id) {
        return journalEntryInterface.findById(id).orElse(null);
    }

    public JournalEntry getJournalEntryById(String id, String userName) {
        JournalEntry journalEntry = getJournalEntryById(id);
        if (journalEntry == null || !userOwnsJournalEntry(userName, id)) {
            return null;
        }
        return journalEntry;
    }

    public void deleteJournalEntryById(String id) {
        journalEntryInterface.deleteById(id);
    }

    public boolean deleteJournalEntryById(String id, String userName) {
        UserEntry user = getUserOrThrow(userName);
        boolean removed = user.getJournalEntries() != null
                && user.getJournalEntries().removeIf(entry -> Objects.equals(entry.getId(), id));

        if (!removed) {
            return false;
        }

        userEntryInterface.save(user);
        journalEntryInterface.deleteById(id);
        return true;
    }

    public boolean userOwnsJournalEntry(String userName, String journalEntryId) {
        UserEntry user = getUserOrThrow(userName);
        return user.getJournalEntries() != null
                && user.getJournalEntries().stream()
                        .anyMatch(entry -> Objects.equals(entry.getId(), journalEntryId));
    }

    private UserEntry getUserOrThrow(String userName) {
        UserEntry user = userEntryInterface.findByUserName(userName);
        if (user == null) {
            throw new RuntimeException("User not found: " + userName);
        }
        return user;
    }
}
