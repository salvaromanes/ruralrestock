package com.tfg.ruralrestock.repository.basic.employment;

import com.tfg.ruralrestock.model.basic.employment.Employment;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmploymentRepository extends MongoRepository<Employment, String> {
    @Query("{'empresa_ofertante': ?0}")
    Optional<List<Employment>> findByEmpresa(String empresa_ofertante);
    @Query("{'nombre': ?0}")
    Optional<Employment> findByNombre(String nombre);
}