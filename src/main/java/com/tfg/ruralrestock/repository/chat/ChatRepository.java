package com.tfg.ruralrestock.repository.chat;

import com.tfg.ruralrestock.model.chat.Chat;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<Chat, String> {
}