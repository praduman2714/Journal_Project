package com.example.journal_app.repository;

import com.example.journal_app.entity.UserEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntryInterface extends MongoRepository<UserEntry, ObjectId> {
    UserEntry findByUserName(String userName);
}
