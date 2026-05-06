package com.example.journal_app.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("journal_entries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JournalEntry {

    @Id
    private String id;
    private String title;
    private String content;

}
