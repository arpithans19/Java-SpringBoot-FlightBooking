package com.flight.controller;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flight.entity.Flight;
import com.flight.model.ResponseTemplate;
import com.flight.service.FlightService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import javax.validation.Valid;

@RestController
@RequestMapping("/flight")
@CrossOrigin(origins = "http://localhost:3000")
public class FlightController {
	
	@Autowired 
	private FlightService flightService;

	@GetMapping("/getallflights")

		public List<Flight> fetchAllFlights() {
			List<Flight> flightList = flightService.getAllFlights();
			return flightList;

		}
	
	@HystrixCommand(fallbackMethod = "handleFlightWithFare")
	@GetMapping("/find/{flightId}")
	public ResponseEntity<ResponseTemplate> getFlightWithFare(@PathVariable("flightId") int flightId) {
    ResponseEntity<ResponseTemplate> responseEntity = null;	
    ResponseTemplate responseTemplate= flightService.getFlightByFare(flightId);
    responseEntity=new ResponseEntity<>(responseTemplate,HttpStatus.OK);
		return responseEntity;
	}
	
	public ResponseEntity<ResponseTemplate> handleFlightWithFare (@PathVariable("flightId") int flightId) {
	    ResponseEntity<ResponseTemplate> responseEntity = null;	
	    ResponseTemplate responseTemplate= flightService.getFlightByFare(flightId);
	    responseEntity=new ResponseEntity<>(responseTemplate,HttpStatus.OK);
			return responseEntity;
		}
	    
	    @GetMapping("/get/{fId}")
		public ResponseEntity<Object> fetchFlightById(@PathVariable("fId") int flightId) {
			
			ResponseEntity<Object> responseEntity = null;		
			Flight flight=flightService.getFlightById(flightId);	
			responseEntity = new ResponseEntity<>(flight,HttpStatus.OK);				
			return responseEntity;
		}
	    
	    @PostMapping("/addFlight")
		public ResponseEntity<Flight> addFlight( @Validated @RequestBody Flight flight) {
	    	Flight newFlight = flightService.saveFlight(flight);
			ResponseEntity<Flight> responseEntity = new ResponseEntity<>(newFlight, HttpStatus.OK);
			return responseEntity;

		}
	    @DeleteMapping("/delete/{flightId}")
		public ResponseEntity<String> removeFlight(@PathVariable("flightId") int flightId) {
	    	flightService.deleteFlight(flightId);
			ResponseEntity<String> responseEntity = new ResponseEntity<>("Flight deleted successfully!!", HttpStatus.OK);
			return responseEntity;
		}

		@PutMapping("/update")
		public ResponseEntity<Flight> modifyFlight(@RequestBody Flight flight) {
			Flight updateFlight = flightService.updateFlight(flight);
			ResponseEntity<Flight> responseEntity = new ResponseEntity<>(updateFlight, HttpStatus.OK);
			return responseEntity;
		}
		
		@GetMapping("/{fromLocation}/{destination}/{date}")
		public List<Flight> fetchFlights(@PathVariable ("fromLocation") String fromLocation,@PathVariable("destination") String destination,@PathVariable ("date")@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
			return flightService.flightSearch(fromLocation, destination, date);
		}
}
