package com.checkin.repositorytests;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.checkin.entity.CheckIn;
import com.checkin.repository.CheckInRepository;

@SpringBootTest
 class CheckInRepositoryTests {
	
	@Mock
	private CheckInRepository checkInRepository;
	
	@Test
	void testSaveCheckIn() {
		CheckIn checkIn=new CheckIn();
		checkIn.setCheckInId(100);
		checkIn.setStatus("checkedin");
		when(checkInRepository.save(checkIn)).thenReturn(checkIn);
		assertEquals(checkIn,checkInRepository.save(checkIn));
		
	}
	
	@Test
	void testCheckInWithFlight() {
		CheckIn checkIn=new CheckIn();
		checkIn.setCheckInId(100);
		checkIn.setStatus("checkedin");
		
//		Flight flight=new Flight();
//		flight.setFlightId(100);
//		flight.setSeatNo("20A");
//
//		CheckInResponse checkInResponse=new CheckInResponse();
//		checkInResponse.setCheckIn(checkIn);
//		checkInResponse.setFlight(flight);
		
		Optional<CheckIn> optionalCheckIn = Optional.of(checkIn);
		when(checkInRepository.findById(100)).thenReturn(optionalCheckIn);
		assertEquals(checkIn.getCheckInId(),100);
	}

}
