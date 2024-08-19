package com.tfg.ruralrestock.repository.basic.livingPlace;

import com.tfg.ruralrestock.model.basic.livingPlace.LivingPlace;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LivingPlaceRepository extends MongoRepository<LivingPlace, String> {
    @Query("{'tipo': ?0}")
    Optional<List<LivingPlace>> findByTipo(String tipo);
    @Query("{'municipio': ?0}")
    Optional<List<LivingPlace>> findByMunicipio(String municipio);
    @Query("{'propietario': ?0}")
    Optional<List<LivingPlace>> findByPropietario(String propietario);
}