package com.flight.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.flight.entity.Flight;

public interface FlightRepository extends MongoRepository<Flight, Integer>{

//	@Query( "{'fromLocation':?0,'destination':?1,'date':?2}")
//	List<Flight> searchByLocation(String fromLocation,String destination,String date);
	
	@Query("{'fromLocation' : :#{#fromLocation}, 'destination' : :#{#destination}, 'date' : :#{#date}}")
	List<Flight> searchByLocation(@Param("fromLocation") String fromLocation, @Param("destination") String destination,@Param("date") LocalDate date);


}
