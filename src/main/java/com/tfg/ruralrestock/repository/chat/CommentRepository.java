package com.tfg.ruralrestock.repository.chat;

import com.tfg.ruralrestock.model.chat.Comment;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends MongoRepository<Comment, String> {
    @Query("{'autor': ?0}")
    Optional<List<Comment>> findByCreador(String autor);
}