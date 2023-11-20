package com.fare.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.fare.entity.Fare;

public interface FareRepository extends MongoRepository<Fare, Integer>{
    @Query("{'farePrice':?0}")
    Optional<Fare> findByPrice(double farePrice);
}
