package com.checkin.controllertests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.checkin.controller.CheckInController;
import com.checkin.entity.CheckIn;
import com.checkin.service.CheckInService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class CheckInControllerTests {
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private CheckInService checkInService;
	private CheckIn checkIn;
	private List<CheckIn> checkInList;
	
	@InjectMocks
	private CheckInController checkInController;
	
	@BeforeEach
	void setUp() {
		checkIn=new CheckIn();
		checkIn.setCheckInId(100);
		checkIn.setStatus("checked-In");
		mockMvc=MockMvcBuilders.standaloneSetup(checkInController).build();
	}
	
	@Test
	void testSaveCheckInController() throws Exception{
		when(checkInService.saveCheckIn(any())).thenReturn(checkIn);
        mockMvc.perform(
                post("/checkin/save").contentType(MediaType.APPLICATION_JSON).content(asJsonString(checkIn)))
                .andExpect(status().isOk());
		verify(checkInService, times(1)).saveCheckIn(any());
   }
	
//	@Test
//	void testGetCheckInByIdController() throws Exception{
//		flight=new Flight();
//		flight.setFlightId(100);
//		flight.setSeatNo("20A");
//		checkInResponse=new CheckInResponse();
//		checkInResponse.setCheckIn(checkIn);
//		checkInResponse.setFlight(flight);
//		when(checkInService.getCheckInByFlightId(100)).thenReturn(checkInResponse);
//		mockMvc.perform(
//				get("/checkin/100").contentType(MediaType.APPLICATION_JSON).content(asJsonString(checkIn)))
//				.andExpect(status().isOk());
//		verify(checkInService,times(1)).getCheckInByFlightId(100);
//	}
	
	
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
