package com.checkin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.checkin.entity.CheckIn;
import com.checkin.exception.CheckInNotFoundException;
import com.checkin.service.CheckInService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import java.util.List;

@RestController
@RequestMapping("/checkin")
@CrossOrigin(origins = "http://localhost:3000")
public class CheckInController {

	@Autowired
	private CheckInService checkInService;

	@PostMapping("/save")
	public ResponseEntity<CheckIn> addCheckIn(@RequestBody CheckIn checkIn) {
		CheckIn newCheckIn = checkInService.saveCheckIn(checkIn);
		ResponseEntity<CheckIn> responseEntity = new ResponseEntity<>(newCheckIn, HttpStatus.OK);
		return responseEntity;
	}

//	@HystrixCommand(fallbackMethod = "handleGetCheckInById")
//	@GetMapping("{cId}")
//	public ResponseEntity<CheckInResponse> getCheckInById(@PathVariable("cId") int checkInId) {
//
//		CheckInResponse checkInResponse = checkInService.getCheckInByFlightId(checkInId);
//		return new ResponseEntity<>(checkInResponse, HttpStatus.OK);
//
//	}
//	public ResponseEntity<CheckInResponse> handleGetCheckInById(@PathVariable("cId") int checkInId) {
//
//		CheckInResponse checkInResponse = checkInService.getCheckInByFlightId(checkInId);
//		return new ResponseEntity<>(checkInResponse, HttpStatus.OK);
//
//	}

	@HystrixCommand(fallbackMethod = "handleFetchCheckInById")
	@GetMapping("/find/{checkInId}")
	public ResponseEntity<Object> fetchFareById(@PathVariable("checkInId") int checkInId) {
		ResponseEntity<Object> responseEntity = null;
		CheckIn checkIn = checkInService.getCheckInById(checkInId);
		responseEntity = new ResponseEntity<Object>(checkIn, HttpStatus.OK);
		return responseEntity;
	}

	public ResponseEntity<Object> handleFetchCheckInById(@PathVariable("checkInId") int checkInId) {
		ResponseEntity<Object> responseEntity = null;
		CheckIn checkIn = checkInService.getCheckInById(checkInId);
		responseEntity = new ResponseEntity<Object>(checkIn, HttpStatus.OK);
		return responseEntity;
	}

	@DeleteMapping("/delete/{checkInId}")
	public ResponseEntity<String> removeCheckIn(@PathVariable("checkInId") int checkInId) {
		checkInService.deleteCheckinById(checkInId);
		ResponseEntity<String> responseEntity = new ResponseEntity<>("CheckIn deleted successfully!!", HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping("/getallcheckin")
	public List<CheckIn> fetchAllFlights() {
		List<CheckIn> checkInList = checkInService.getAllCheckIn();
		return checkInList;

	}

//	@GetMapping("/{pnrNo}")
//	public ResponseEntity<Object> fetchByPnrNo(@PathVariable("pnrNo") long pnrNo) {
//		ResponseEntity<Object> responseEntity = null;
//		CheckIn checkIn = checkInService.getByCheckInByPnrNo(pnrNo);
//		responseEntity = new ResponseEntity<Object>(checkIn, HttpStatus.OK);
//		return responseEntity;
//	}
}
