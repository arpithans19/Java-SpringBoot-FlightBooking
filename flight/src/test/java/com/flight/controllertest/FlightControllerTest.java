package com.flight.controllertest;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flight.controller.FlightController;
import com.flight.entity.Flight;
import com.flight.model.Fare;
import com.flight.model.ResponseTemplate;
import com.flight.service.FlightService;

@ExtendWith(MockitoExtension.class)
public class FlightControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@InjectMocks
	private FlightController flightController;
	
	@Mock
	private FlightService flightService;
	private Flight flight;
	private ResponseTemplate responseTemplate;
	private List<Flight> flights;
	 
	@BeforeEach
    public void setUp() { 
		
		flight=new Flight();
        flight.setFlightId(100);
		flight.setFromLocation("HYD");
        flight.setDestination("DEL");
        flight.setFlightName("Air India");
        flight.setFlightType("Domestic");
        flight.setFarePrice(1000);
        mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();
    }
	
	@Test
    void saveFlightControllerTest() throws Exception {
        when(flightService.saveFlight(any())).thenReturn(flight);
        mockMvc.perform(
                post("/flight/addFlight").contentType(MediaType.APPLICATION_JSON).content(asJsonString(flight)))
                .andExpect(status().isOk());
		verify(flightService, times(1)).saveFlight(any());
   }
	
	@Test
    void getAllFlightsControllerTest() throws Exception {
        when(flightService.getAllFlights()).thenReturn(flights);
        mockMvc.perform(get("/flight/getallflights").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(flights))).andDo(print());
        verify(flightService, times(1)).getAllFlights();
   }

	@Test
    void getFlightByIdControllerTest() throws Exception {
        when(flightService.getFlightById(flight.getFlightId())).thenReturn(flight);
        mockMvc.perform(get("/flight/get/100").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(flight))).andDo(print());
        verify(flightService, times(1)).getFlightById(100);
   }
	@Test
    void getFlghtControllerByFareTest() throws Exception {
		Fare fare=new Fare();
		fare.setFareId(10);
		fare.setFareType("economy");
		fare.setFarePrice(3000);
		responseTemplate=new ResponseTemplate();
		responseTemplate.setFare(fare);
		responseTemplate.setFlight(flight);
        when(flightService.getFlightByFare(flight.getFlightId())).thenReturn(responseTemplate);
        mockMvc.perform(get("/flight/find/100").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(responseTemplate))).andDo(print());
        verify(flightService, times(1)).getFlightByFare(100);
   }

	@Test
	void deleteFlightControllerById() throws Exception{
		mockMvc.perform(delete("/flight/delete/100").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(flight))).andDo(print());
		verify(flightService,times(1)).deleteFlight(100);
	}
	
	@Test
	void updateFlightControllerById() throws Exception{
		when(flightService.updateFlight(any())).thenReturn(flight);
		mockMvc.perform(put("/flight/update").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(flight))).andDo(print());
		verify(flightService,times(1)).updateFlight(any());
	}
	
	@Test
	void getFlightControllerByLocation() throws Exception{
		flight.setDate(LocalDate.of(2022,9,12));
		when(flightService.flightSearch(flight.getFromLocation(),flight.getDestination(),flight.getDate())).thenReturn(flights);
		mockMvc.perform(get("/flight/HYD/DEL/2022-09-12").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(flights))).andDo(print());
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
