package com.booking.service;

import java.util.List;

import com.booking.entity.Booking;
import com.booking.model.BookingResponse;

public interface BookingService {
	
    public BookingResponse saveBooking(Booking booking);
	
	public void deleteBooking(int bookingId);

	public List<Booking> getBookings();

	public Booking getBookingById(int bookingId);

	public int getSequenceNumber(String sequenceName);
	
	Booking getBookingByPnrNo (int pnrNo);
}
