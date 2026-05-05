package com.example.journal_app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.journal_app.entity.JournalEntry;

public interface JournalEntryInterface extends MongoRepository<JournalEntry, String> {

}
