package com.checkin.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import java.util.Objects;
import java.util.Optional;

import com.checkin.entity.DBSequence;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.client.RestTemplate;

import com.checkin.entity.CheckIn;
import com.checkin.exception.CheckInNotFoundException;
import com.checkin.repository.CheckInRepository;
import com.checkin.service.CheckInService;
import com.checkin.service.CheckInServiceImpl;

import javax.sound.midi.Sequence;

@SpringBootTest
class CheckInServiceTests {

	@InjectMocks
	private CheckInService checkInService=new CheckInServiceImpl();
	
	@Mock
	private CheckInRepository checkInRepository;
	
	@Mock
	private RestTemplate restTemplate;
	private MongoOperations mongoOperations;
	private DBSequence dbSequence;
	
	@Test
	void testSaveCheckIn() {
		CheckIn checkIn=new CheckIn();
		checkIn.setCheckInId(100);
		checkIn.setStatus("checked in");
		checkIn.setSeatNo(20);
		
		when(checkInRepository.save(checkIn)).thenReturn(checkIn);
//		CheckIn newCheckIn=checkInService.saveCheckIn(checkIn);
//		assertEquals(newCheckIn, checkIn);
	}



	//	@Test
//	void testCheckInWithFlight() {
//		CheckIn checkIn=new CheckIn();
//		checkIn.setCheckInId(100);
//		checkIn.setStatus("checkedin");
//		checkIn.setFlightId(100);
//
//		Flight flight=new Flight();
//		flight.setFlightId(100);
//		flight.setSeatNo("20A");
//
//		Optional<CheckIn> optionalCheckIn = Optional.of(checkIn);
//
//		when(checkInRepository.findById(100)).thenReturn(optionalCheckIn);
//		CheckInResponse response=checkInService.getCheckInByFlightId(100);
//	    response.setCheckIn(checkIn);
//	    response.setFlight(flight);
//
//	    assertEquals(response.getFlight(),flight);
//
//	}
//	@Test
//     void testGetCheckInByIdWithException() {
//
//	 when(checkInRepository.findById(100)).thenThrow(CheckInNotFoundException.class);
//
//	 assertThrows(CheckInNotFoundException.class, () -> checkInService.deleteCheckinById(100));
//	}
  @Test
  void testGetCheckInById() {

		CheckIn checkIn = new CheckIn();

		checkIn.setCheckInId(100);
		checkIn.setStatus("checked in");
		checkIn.setSeatNo(20);

		Optional<CheckIn> optionalCheckIn= Optional.of(checkIn);

		when(checkInRepository.findById(100)).thenReturn(optionalCheckIn);
		CheckIn newCheckIn = checkInService.getCheckInById(100);
		assertEquals(20,newCheckIn.getSeatNo());

}

	@Test
	void testGetFareByIdWithException() {

		when(checkInRepository.findById(100)).thenThrow(CheckInNotFoundException.class);

		assertThrows(CheckInNotFoundException.class, () -> checkInService.getCheckInById(100),"checkIn not found with Id");
	}


	@Test
	void testDeleteFare() {
		CheckIn checkIn = new CheckIn();

		checkIn.setCheckInId(100);
		checkIn.setStatus("checked in");
		checkIn.setSeatNo(20);

		Optional<CheckIn> optionalCheckIn= Optional.of(checkIn);

		when(checkInRepository.findById(100)).thenReturn(optionalCheckIn);
		checkInService.deleteCheckinById(100);

	}
	@Test
	void testDeleteFareByIdWithException() {

		when(checkInRepository.findById(100)).thenThrow(CheckInNotFoundException.class);

		assertThrows(CheckInNotFoundException.class, () -> checkInService.getCheckInById(100));
	}

}
