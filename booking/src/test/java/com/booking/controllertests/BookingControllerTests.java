package com.booking.controllertests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.booking.controller.BookingController;
import com.booking.entity.Booking;
import com.booking.model.BookingResponse;
import com.booking.model.Fare;
import com.booking.model.Flight;
import com.booking.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@InjectMocks
	private BookingController bookingController;
	
	@Mock
	private BookingService bookingService;
	private Flight flight;
	private Booking booking;
	private Fare fare;
	private BookingResponse bookingResponse;
	
	@BeforeEach
	void setUp() {
		booking=new Booking();
		booking.setBookingId(1);
		booking.setFirstName("Shaik");
		booking.setLastName("Arshad");
		booking.setGender("male");
		booking.setFarePrice(2000);
		booking.setFlightId(1);
		mockMvc=MockMvcBuilders.standaloneSetup(bookingController).build();
	}
	
	@Test
	void testsaveBookingController() throws Exception{
		fare=new Fare();
		fare.setFareId(1);
		fare.setFarePrice(2000);
		
		bookingResponse=new BookingResponse();
		bookingResponse.setBooking(booking);
		bookingResponse.setFlight(flight);
		bookingResponse.setFare(fare);
		
		when(bookingService.saveBooking(any())).thenReturn(bookingResponse);
		mockMvc.perform(post("/booking/save").contentType(MediaType.APPLICATION_JSON).content(asJsonString(booking)))
		.andExpect(status().isOk());
		verify(bookingService,times(1)).saveBooking(any());
	}
	
	@Test
	void testGetBookingController() throws Exception{
		mockMvc.perform(delete("/booking/delete/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(booking)))
		.andExpect(status().isOk());
		verify(bookingService,times(1)).deleteBooking(booking.getBookingId());
	}
	public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
