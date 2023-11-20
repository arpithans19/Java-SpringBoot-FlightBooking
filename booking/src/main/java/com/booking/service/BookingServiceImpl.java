package com.booking.service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.booking.entity.Booking;
import com.booking.entity.DbSequence;
import com.booking.exception.BookingNotFoundException;
import com.booking.model.BookingResponse;
import com.booking.model.Fare;
import com.booking.model.Flight;
import com.booking.repository.BookingRepository;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MongoOperations mongoOperations;


	Logger logger=org.slf4j.LoggerFactory.getLogger(BookingServiceImpl.class);

	@Override
	public BookingResponse saveBooking(Booking booking) {
		
		Random random=new Random();
		booking.setPnrNo(random.nextInt(99999999)+100000);
//		checkIn.setSeatNo(random.nextInt(500)+100);
		BookingResponse response = new BookingResponse();

		booking.setBookingId(getSequenceNumber(Booking.SEQUENCE_NAME));


		Booking newBooking = bookingRepository.save(booking);

		Flight flight = restTemplate.getForObject("http://flight-service/flight/get/" + booking.getFlightId(),
				Flight.class);
		Fare fare = restTemplate
				.getForObject("http://fare-service/fare/" + booking.getFarePrice(), Fare.class);
		response.setFare(fare);
		response.setFlight(flight);
		response.setBooking(newBooking);
		logger.warn("save booking");
		return response;
	}

	@Override
	public void deleteBooking(int bookingId) {
		Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
		if(optionalBooking.isEmpty()) {
			throw new BookingNotFoundException("Booking not found with Id: "+bookingId);
		}
		bookingRepository.deleteById(bookingId);
	}

	@Override
	public List<Booking> getBookings() {
		List<Booking> booking= bookingRepository.findAll();
		return booking;

	}

	@Override
	public Booking getBookingById(int bookingId) {
		Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
		if (optionalBooking.isEmpty()) {
			throw new BookingNotFoundException("Booking is not found with this ID: " + bookingId);
		}
		Booking booking = optionalBooking.get();
		return booking;
		}

	@Override
	public int getSequenceNumber(String sequenceName) {
		//generate sequence no
		Query query=new Query(Criteria.where("id").is(sequenceName));
		//update the sequence no
		Update update=new Update().inc("seq",1);
		//modify in document
		DbSequence counter=mongoOperations.findAndModify(query,update, options().returnNew(true).upsert(true), DbSequence.class);
		return !Objects.isNull(counter)? counter.getSeq():1;
	}

	@Override
	public Booking getBookingByPnrNo(int pnrNo) {		
			Optional<Booking> optionalFetchById= Optional.ofNullable(bookingRepository.findByPnr(pnrNo));
			if(optionalFetchById.isEmpty()) {
				throw new BookingNotFoundException("Booking not found with Id: "+ pnrNo);
			}
			return optionalFetchById.get();
		
	}

}
