package com.tfg.ruralrestock.repository.basic.event;

import com.tfg.ruralrestock.model.basic.event.Event;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends MongoRepository<Event, String> {
    @Query("{'tipo': ?0}")
    Optional<List<Event>> findByType(String tipo);
    @Query("{'municipio': ?0}")
    Optional<List<Event>> findByMunicipio(String municipio);
    @Query("{'nombre': ?0}")
    Optional<Event> findByName(String nombre);
}