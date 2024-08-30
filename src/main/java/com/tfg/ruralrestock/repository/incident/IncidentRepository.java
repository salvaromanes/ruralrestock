package com.tfg.ruralrestock.repository.incident;

import com.tfg.ruralrestock.model.incident.Incident;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IncidentRepository extends MongoRepository<Incident, String> {
}