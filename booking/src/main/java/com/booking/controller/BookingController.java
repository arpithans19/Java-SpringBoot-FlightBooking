package com.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.booking.entity.Booking;
import com.booking.model.BookingResponse;
import com.booking.service.BookingService;

@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {

	

	@Autowired
	private BookingService bookingService;



//	@Autowired
//	private SequenceGeneratorService  sequenceGeneratorService;

//	@HystrixCommand(fallbackMethod = "handleAddBooking")
	@PostMapping("/save")
	public ResponseEntity<BookingResponse>  addBooking(@Validated @RequestBody Booking booking) {
		BookingResponse newResponse= bookingService.saveBooking(booking);
		 return new ResponseEntity<>(newResponse,HttpStatus.OK); 
	}
//	public ResponseEntity<BookingResponse>  handleAddBooking(@RequestBody Booking booking) {
//		BookingResponse newResponse= bookingService.saveBooking(booking);
//		 return new ResponseEntity<>(newResponse,HttpStatus.OK);
//	}

	@DeleteMapping("/delete/{bId}")
//	@RequestMapping(path="/booking" ,method=RequestMethod.DELETE)
	public ResponseEntity<String> removePassenger(@PathVariable("bId") int bookingId) {
		bookingService.deleteBooking(bookingId);
		return new ResponseEntity<>("Booking Deleted Successfully", HttpStatus.OK);
	}

	@GetMapping("/findAllBooking")
	public List<Booking> getAllBookings() {
		return bookingService.getBookings();

	}

	@GetMapping("/find/{bookingId}")
	public ResponseEntity<Object> fetchBookingById(@PathVariable("bookingId") int booingId) {
		ResponseEntity<Object> responseEntity = null;
		Booking booking = bookingService.getBookingById(booingId);
		responseEntity = new ResponseEntity<>(booking, HttpStatus.OK);
		return responseEntity;
	}
	@GetMapping("/{pnrNo}")
	public ResponseEntity<Object> fetchByPnrNo(@PathVariable("pnrNo") int pnrNo) {
		ResponseEntity<Object> responseEntity = null;
		Booking booking = bookingService.getBookingByPnrNo(pnrNo);
		responseEntity = new ResponseEntity<>(booking, HttpStatus.OK);
		return responseEntity;
	}

}

