package com.flight.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.flight.entity.Flight;
import com.flight.model.ResponseTemplate;

public interface FlightService {
	
	List<Flight> getAllFlights();
	
	ResponseTemplate getFlightByFare(int flightId);

	Flight saveFlight(Flight flight);
	
	void deleteFlight(int flightId);
	
	Flight updateFlight(Flight flight);

	Flight getFlightById(int flightId);

	List<Flight> flightSearch(String fromLocation,String destination,LocalDate date);

	public int getSequenceNumber(String sequenceName);
}
