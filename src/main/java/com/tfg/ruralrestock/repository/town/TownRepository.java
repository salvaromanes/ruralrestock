package com.tfg.ruralrestock.repository.town;

import com.tfg.ruralrestock.model.town.Town;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TownRepository extends MongoRepository<Town, String> {
}