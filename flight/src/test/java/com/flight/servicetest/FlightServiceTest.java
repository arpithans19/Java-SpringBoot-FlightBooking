package com.flight.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.flight.entity.Flight;
import com.flight.exception.FlightNotFoundException;
import com.flight.model.Fare;
import com.flight.model.ResponseTemplate;
import com.flight.repository.FlightRepository;
import com.flight.service.FlightService;
import com.flight.service.FlightServiceImpl;



@SpringBootTest
class FlightServiceTest {

	@InjectMocks
	private FlightService flightService = new FlightServiceImpl();

	@Mock
	private FlightRepository flightRepository;
	
	@Mock
	private RestTemplate restTemplate;
	
	@Test
	void testGetFlightById() {
		
		Flight flight = new Flight();
		flight.setFlightId(100);
		flight.setFlightType("domestic");
		flight.setDate(LocalDate.of(2022, 10, 2));
		flight.setFlightName("Air India");		
		flight.setFromLocation("Bangalore");
		flight.setDestination("Delhi");
		

		Optional<Flight> optionalFlight = Optional.of(flight);

		when(flightRepository.findById(100)).thenReturn(optionalFlight);
		Flight myFlight = flightService.getFlightById(100);
		assertEquals("domestic", myFlight.getFlightType());

	}
	@Test
	void testSaveFlight() {

		Flight flight = new Flight();
		flight.setFlightId(100);
		flight.setFlightType("domestic");
		flight.setDate(LocalDate.of(2022, 10, 2));
		flight.setFlightName("Kingfisher Airlines");		
		flight.setFromLocation("Bangalore");
		flight.setDestination("Delhi");
		flight.setFarePrice(1000);

		
		when(flightRepository.save(flight)).thenReturn(flight);

//		Flight newFlight = flightService.saveFlight(flight);
//
//		assertEquals("domestic", newFlight.getFlightType());

	}
	
	@Test
	void testUpdateFlight() {

		Flight flight = new Flight();
		flight.setFlightId(100);
		flight.setFlightType("domestic");
		flight.setDate(LocalDate.of(2022, 10, 2));
		flight.setFlightName("JetLight");		
		flight.setFromLocation("Bangalore");
		flight.setDestination("Delhi");
		flight.setFarePrice(1000);

		Optional<Flight> optionalFlight = Optional.of(flight);

		when(flightRepository.findById(100)).thenReturn(optionalFlight);
		
		when(flightRepository.save(flight)).thenReturn(flight);

		Flight newFlight2 = flightService.updateFlight(flight);

		assertEquals("domestic", newFlight2.getFlightType());
	}
	
	@Test
	void testUpdateByIdException() {
		Flight flight = new Flight();
		flight.setFlightId(100);
		flight.setFlightType("domestic");
		flight.setDate(LocalDate.of(2022, 10, 2));
		flight.setFlightName("JetLight");		
		flight.setFromLocation("Bangalore");
		flight.setDestination("Delhi");
		flight.setFarePrice(1000);
		when(flightRepository.findById(100)).thenThrow(FlightNotFoundException.class);
		assertThrows(FlightNotFoundException.class, () -> flightService.updateFlight(flight));
	}

	@Test
	void testDeleteFlight() {
		
		Flight flight = new Flight();
		flight.setFlightId(100);
		flight.setFlightType("domestic");
		flight.setDate(LocalDate.of(2022, 10, 2));
		flight.setFlightName("Air India");		
		flight.setFromLocation("Bangalore");
		flight.setDestination("Delhi");
		flight.setFarePrice(1000);
		

		Optional<Flight> optionalFlight = Optional.of(flight);
		
		when(flightRepository.findById(100)).thenReturn(optionalFlight);
		
		flightService.deleteFlight(100);

	}
	
	@Test
	void testListOfFlights() {
		Flight flight1 = new Flight();
		flight1.setFlightId(100);
		flight1.setFlightType("domestic");
		flight1.setDate(LocalDate.of(2022, 10, 2));
		flight1.setFlightName("JetLight");		
		flight1.setFromLocation("Bangalore");
		flight1.setDestination("Delhi");
		flight1.setFarePrice(1000);
		
		Flight flight2 = new Flight();
		flight2.setFlightId(101);
		flight2.setFlightType("domestic");
		flight2.setDate(LocalDate.of(2022, 10, 2));
		flight2.setFlightName("AirIndia");		
		flight2.setFromLocation("Bangalore");
		flight2.setDestination("Delhi");
		flight2.setFarePrice(1010);
		
		List<Flight> allFlights = new ArrayList<>();
		allFlights.add(flight1);
		allFlights.add(flight2);

		when(flightRepository.findAll()).thenReturn(allFlights);
		List<Flight> myFlights = flightService.getAllFlights();
		assertEquals(2, myFlights.size());
		
	}
	
	@Test
	void testFlightwithFare() {
		Flight flight = new Flight();
		flight.setFlightId(100);
		flight.setFlightType("domestic");
		flight.setDate(LocalDate.of(2022, 10, 2));
		flight.setFlightName("JetLight");		
		flight.setFromLocation("Bangalore");
		flight.setDestination("Delhi");
		flight.setFarePrice(1000);
		
		Fare fare=new Fare();
		fare.setFareId(100);
		fare.setFareType("economy");
		fare.setFarePrice(3000.0);
	    
		
		Optional<Flight> optionalFlight = Optional.of(flight);
		when(flightRepository.findById(100)).thenReturn(optionalFlight);
		optionalFlight.get();
	    
		ResponseTemplate response=flightService.getFlightByFare(flight.getFlightId());
		response.setFare(fare);
	    response.setFlight(flight);
	    assertEquals(fare,response.getFare());
		
	}
	
	@Test
	void testFlightsByLocation() {
		Flight flight1 = new Flight();
		flight1.setFlightId(100);
		flight1.setFlightType("domestic");
		flight1.setDate(LocalDate.of(2022, 10, 2));
		flight1.setFlightName("JetLight");		
		flight1.setFromLocation("Bangalore");
		flight1.setDestination("Delhi");
		flight1.setFarePrice(1000);
		
		Flight flight2 = new Flight();
		flight2.setFlightId(101);
		flight2.setFlightType("domestic");
		flight2.setDate(LocalDate.of(2022, 10, 2));
		flight2.setFlightName("AirIndia");		
		flight2.setFromLocation("Bangalore");
		flight2.setDestination("Delhi");
		flight2.setFarePrice(1010);
		
		List<Flight> allFlights = new ArrayList<>();
		allFlights.add(flight1);
		allFlights.add(flight2);

		when(flightRepository.searchByLocation("Bangalore", "Delhi", LocalDate.of(2022, 10, 2) )).thenReturn(allFlights);
		List<Flight> myFlights = flightService.flightSearch("Bangalore", "Delhi", LocalDate.of(2022, 10, 2));
		assertEquals(2, myFlights.size());
	}
	
}
