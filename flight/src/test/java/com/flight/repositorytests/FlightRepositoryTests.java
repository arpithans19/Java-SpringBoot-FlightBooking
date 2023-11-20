package com.flight.repositorytests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.flight.entity.Flight;
import com.flight.model.Fare;
import com.flight.model.ResponseTemplate;
import com.flight.repository.FlightRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class FlightRepositoryTests {
	
	@Mock
	private FlightRepository flightRepository;
	private Flight flight;
	private Fare fare;
	private ResponseTemplate responseTemplate;
	
	@BeforeEach
	void setUp() {
		flight=new Flight();
		flight.setFlightId(100);
		flight.setFromLocation("HYD");
        flight.setDestination("DEL");
        flight.setDate(LocalDate.of(2022,9,12));
        flight.setFlightName("Air India");
        flight.setFlightType("Domestic");
        flight.setFarePrice(1000);
	}

	@Test
	void saveFlightRepositoryTest() {
		flightRepository.save(flight);
		assertThat(flight.getFlightId()).isPositive();
	}
	
//	@Test
//	void getFlightRepositoryTest() {
//		flightRepository.save(flight);
//		Flight newFlight=flightRepository.findById(flight.getFlightId()).get();
//		assertNotNull(newFlight);
//		assertEquals(flight.getFlightId(),newFlight.getFlightId());
//	}
}
