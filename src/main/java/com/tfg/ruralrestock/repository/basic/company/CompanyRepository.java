package com.tfg.ruralrestock.repository.basic.company;

import com.tfg.ruralrestock.model.basic.company.Company;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends MongoRepository<Company, String> {
    @Query("{'propietario': ?0}")
    Optional<List<Company>> findByPropietario(String propietario);
    @Query("{'nombre': ?0}")
    Optional<Company> findByName(String name);
}