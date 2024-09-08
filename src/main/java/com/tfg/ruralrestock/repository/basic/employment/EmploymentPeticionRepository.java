package com.tfg.ruralrestock.repository.basic.employment;

import com.tfg.ruralrestock.model.basic.employment.PeticionEmployment;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmploymentPeticionRepository extends MongoRepository<PeticionEmployment, String> {
    @Query("{'creador': ?0}")
    Optional<List<PeticionEmployment>> findByCreador(String creador);
}
