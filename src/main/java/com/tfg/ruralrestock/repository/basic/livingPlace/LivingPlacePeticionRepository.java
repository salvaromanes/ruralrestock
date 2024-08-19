package com.tfg.ruralrestock.repository.basic.livingPlace;

import com.tfg.ruralrestock.model.basic.livingPlace.PeticionNewLivingPlace;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LivingPlacePeticionRepository extends MongoRepository<PeticionNewLivingPlace, String> {
}
