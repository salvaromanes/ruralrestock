package com.tfg.ruralrestock.repository.basic.livingPlace;

import com.tfg.ruralrestock.model.basic.livingPlace.PeticionNewLivingPlace;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LivingPlacePeticionRepository extends MongoRepository<PeticionNewLivingPlace, String> {
    @Query("{'creador': ?0}")
    Optional<List<PeticionNewLivingPlace>> findByCreador(String creador);
}