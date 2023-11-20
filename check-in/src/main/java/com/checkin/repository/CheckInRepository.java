package com.checkin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.checkin.entity.CheckIn;
import org.springframework.data.mongodb.repository.Query;

public interface CheckInRepository extends MongoRepository<CheckIn, Integer>{

//    @Query("{'pnrNo':?0}")
//    CheckIn findByPnr(long pnrNo);
}
