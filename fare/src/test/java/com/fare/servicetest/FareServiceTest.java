package com.fare.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.fare.entity.Fare;
import com.fare.exception.FareNotFoundException;
import com.fare.repository.FareRepository;
import com.fare.service.FareService;
import com.fare.service.FareServiceImpl;

@SpringBootTest
class FareServiceTest {

	@InjectMocks
	private FareService fareService = new FareServiceImpl();

	@Mock
	private FareRepository fareRepository;
	
	@Test
	void testGetFareById() {
		Fare fare = new Fare();

		fare.setFareId(100);
		fare.setFareType("economy");
		fare.setFarePrice(1000.00);

		Optional<Fare> optionalFare = Optional.of(fare);

		when(fareRepository.findById(100)).thenReturn(optionalFare);
		//FareService method
		Fare myFare = fareService.getFareById(100);
		assertEquals(1000.00,myFare.getFareType());

	}
	
	@Test
    void testGetFareByIdWithException() {

	 when(fareRepository.findById(100)).thenThrow(FareNotFoundException.class);

	 assertThrows(FareNotFoundException.class, () -> fareService.getFareById(100),"fare not found with Id");
	}
	
	
	@Test
	void testDeleteFare() {
		Fare fare = new Fare();

		fare.setFareId(100);
		fare.setFareType("premium");
		fare.setFarePrice(2000.00);

		Optional<Fare> optionalFare = Optional.of(fare);

		when(fareRepository.findById(100)).thenReturn(optionalFare);
		
		fareService.deleteFare(100);

	}
	@Test
    void testDeleteFareByIdWithException() {

	 when(fareRepository.findById(100)).thenThrow(FareNotFoundException.class);

	 assertThrows(FareNotFoundException.class, () -> fareService.getFareById(100));
	}
	
	@Test
	void testSaveUser() {
		Fare fare = new Fare();

		fare.setFareId(100);
		fare.setFareType("premium");
		fare.setFarePrice(3000.00);

		when(fareRepository.save(fare)).thenReturn(fare);

		Fare newFare = fareService.saveFare(fare);

		assertEquals(3000.00,newFare.getFareType());

	}

	@Test
	void testUpdateFare() {

		Fare fare = new Fare();
		fare.setFareId(100);
		fare.setFareType("economy");
		fare.setFarePrice(1000.00);
		

		Optional<Fare> optionalFare = Optional.of(fare);

		when(fareRepository.findById(100)).thenReturn(optionalFare);
		when(fareRepository.save(fare)).thenReturn(fare);

		Fare updateFare = fareService.modifyFare(fare);
		
		assertEquals(1000,updateFare.getFareType());
	}

	@Test
    void testUpdateFareByIdWithException() {

	 when(fareRepository.findById(100)).thenThrow(FareNotFoundException.class);

	 assertThrows(FareNotFoundException.class, () -> fareService.getFareById(100));
	}
}

