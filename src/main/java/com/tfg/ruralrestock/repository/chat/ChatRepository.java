package com.tfg.ruralrestock.repository.chat;

import com.tfg.ruralrestock.model.chat.Chat;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends MongoRepository<Chat, String> {
    @Query("{'autor': ?0}")
    Optional<List<Chat>> findByCreador(String autor);
}