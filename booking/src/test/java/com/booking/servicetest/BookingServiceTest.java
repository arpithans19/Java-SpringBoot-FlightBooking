package com.booking.servicetest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.booking.entity.Booking;
import com.booking.exception.BookingNotFoundException;
import com.booking.model.BookingResponse;
import com.booking.model.Fare;
import com.booking.model.Flight;
import com.booking.repository.BookingRepository;
import com.booking.service.BookingService;
import com.booking.service.BookingServiceImpl;

@SpringBootTest
class BookingServiceTest {
		
		@InjectMocks
		private BookingService bookingService = new BookingServiceImpl();

		@Mock
		private BookingRepository bookingRepository;
		
		@Mock
		private RestTemplate restTemplate;
		
		@Test
		void testSaveBooking() {
			
		
			Booking booking=new Booking();
			booking.setBookingId(100);
			booking.setFirstName("Arpitha");
			booking.setLastName("Narayanaswamy");
			booking.setGender("Male");
			booking.setFlightId(130);
			booking.setFarePrice(2000);
			
			Fare fare=new Fare();
			fare.setFareId(120);
			fare.setFarePrice(2000);
			
			Flight flight=new Flight();
			flight.setFlightId(130);
			flight.setFromLocation("Bangalore");
			flight.setDestination("hyderabad");
			
			Booking newBooking=bookingRepository.save(booking);
			
			when(newBooking).thenReturn(booking);
		    BookingResponse response=bookingService.saveBooking(booking);
		    response.setBooking(newBooking);
		    response.setFare(fare);
		    response.setFlight(flight);
		    
		    assertEquals(fare,response.getFare());
		    
		    
			

			
		}

		@Test
		void testDeleteBooking() {
			
			Booking booking=new Booking();
			booking.setBookingId(100);
			booking.setFirstName("Arpitha");
			booking.setLastName("Narayanaswamy");
			booking.setGender("Male");
			booking.setFlightId(130);
			booking.setFarePrice(2000);
			

			Optional<Booking> optionalBooking = Optional.of(booking);

			when(bookingRepository.findById(100)).thenReturn(optionalBooking);
			
			bookingService.deleteBooking(100);

		}
		@Test
	    void testDeleteBookingByIdWithException() {

		 when(bookingRepository.findById(100)).thenThrow(BookingNotFoundException.class);

		 assertThrows(BookingNotFoundException.class, () -> bookingService.deleteBooking(100));
		}

}
