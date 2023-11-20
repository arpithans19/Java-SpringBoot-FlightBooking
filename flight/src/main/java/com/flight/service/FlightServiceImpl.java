package com.flight.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.flight.entity.DBSequence;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.flight.entity.Flight;
import com.flight.exception.FlightNotFoundException;
import com.flight.model.Fare;
import com.flight.model.ResponseTemplate;
import com.flight.repository.FlightRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
public class FlightServiceImpl implements FlightService{
	
	@Autowired
	private FlightRepository flightRepository;
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MongoOperations mongoOperations;
	
	Logger logger=org.slf4j.LoggerFactory.getLogger(FlightServiceImpl.class);

	@Override
	public List<Flight> getAllFlights() {
		List<Flight> flights=flightRepository.findAll();
		if(flights.isEmpty()) {
			throw new FlightNotFoundException("Flights Not available");
		}
		return flights;
	}
	
	@Override

	@HystrixCommand(fallbackMethod = "handleFlightByFareId")
	public ResponseTemplate getFlightByFare(int flightId) {
		ResponseTemplate response= new ResponseTemplate();
		Optional<Flight> optionalFlight=flightRepository.findById(flightId);
		logger.warn("get flight by fare Id");
		if(optionalFlight.isEmpty()) {
			throw new FlightNotFoundException("Flight not found with Id: "+flightId);
		}
		Flight flight=optionalFlight.get();
		Fare fare=restTemplate.getForObject("http://fare-service/fare/"+flight.getFarePrice(), Fare.class);
		response.setFare(fare);
		response.setFlight(flight);
		return response;
	}
	public ResponseTemplate handleFlightByFareId(int flightId) {
		ResponseTemplate response= new ResponseTemplate();
		Optional<Flight> optionalFlight=flightRepository.findById(flightId);
		logger.warn("get flight by fare Id");
		if(optionalFlight.isEmpty()) {
			throw new FlightNotFoundException("Flight not found with Id: "+flightId);
		}
		Flight flight=optionalFlight.get();
		Fare fare=restTemplate.getForObject("http://fare-service/fare/"+flight.getFarePrice(), Fare.class);
		response.setFare(fare);
		response.setFlight(flight);
		return response;
	}
	@Override
	public Flight getFlightById(int flightId) {
		Optional<Flight> optionalFlight= flightRepository.findById(flightId);
		logger.warn("get flight by Id");
		if (optionalFlight.isEmpty()) {

			throw new FlightNotFoundException("Flight Not found with id: " + flightId);
		}
		return optionalFlight.get();
	}
	@Override
	public Flight saveFlight(Flight flight) {
		flight.setFlightId(getSequenceNumber(Flight.SEQUENCE_NAME));
		return flightRepository.save(flight);
	}

	@Override
	public int getSequenceNumber(String sequenceName) {
		//generate sequence no
		Query query=new Query(Criteria.where("id").is(sequenceName));
		//update the sequence no
		Update update=new Update().inc("seq",1);
		//modify in document
		DBSequence counter=mongoOperations.findAndModify(query,update, options().returnNew(true).upsert(true),DBSequence.class);
		return !Objects.isNull(counter)? counter.getSeq():1;
	}

	@Override
	public void deleteFlight(int flightId) {
		Optional<Flight> optionalFlight = flightRepository.findById(flightId);
		if (optionalFlight.isEmpty()) {
			throw new FlightNotFoundException("Flight Not existing with Id:" + flightId);
		}
		flightRepository.deleteById(flightId);
	}

	@Override
	public Flight updateFlight(Flight flight) {
		Optional<Flight> optionalFlight = flightRepository.findById(flight.getFlightId());
		if (optionalFlight.isEmpty()) {
			throw new FlightNotFoundException("Flight Not existing with Id:" + flight.getFlightId());
		}
		return flightRepository.save(flight);
	}

	@Override
	public List<Flight> flightSearch(String fromLocation, String destination, LocalDate date) {
		List<Flight> flights= flightRepository.searchByLocation(fromLocation, destination, date);
		if(flights.isEmpty()) {
			throw new FlightNotFoundException("Flights Not available");
		}
		return flights ;
		
	}
}