package com.booking.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.booking.entity.Booking;

public interface BookingRepository extends MongoRepository<Booking,Integer>{


	@Query("{'pnrNo':?0}")
    Booking findByPnr(int pnrNo);

}
