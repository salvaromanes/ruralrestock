package com.tfg.ruralrestock.repository.basic.event;

import com.tfg.ruralrestock.model.basic.event.PeticionNewEvent;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EventPeticionRepository extends MongoRepository<PeticionNewEvent, String> {
    @Query("{'creador': ?0}")
    Optional<List<PeticionNewEvent>> findByCreador(String creador);
}