package com.tfg.ruralrestock.repository.basic.event;

import com.tfg.ruralrestock.model.basic.event.PeticionNewEvent;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventPeticionRepository extends MongoRepository<PeticionNewEvent, String> {
}
