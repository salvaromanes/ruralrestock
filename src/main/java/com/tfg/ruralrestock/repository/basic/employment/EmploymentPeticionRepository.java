package com.tfg.ruralrestock.repository.basic.employment;

import com.tfg.ruralrestock.model.basic.employment.PeticionEmployment;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmploymentPeticionRepository extends MongoRepository<PeticionEmployment, String> {
}
