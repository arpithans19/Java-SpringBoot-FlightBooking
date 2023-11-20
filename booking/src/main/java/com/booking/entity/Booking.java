package com.booking.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Document(collection = "booking")
public class Booking {

	@Transient
	public static final  String SEQUENCE_NAME ="booking_sequence";

	@Id
	@NotNull
//	@Max(value = 99999, message = "Flight number cannot exceed 5 digits")
	private int bookingId;

	@NotNull
	@Size(min = 3, message = "FirstName should be minimum of 3 characters")
	private String firstName;

	@NotNull
//	@Size(min = 1, message = " LastName should contain minimum of 1 character")
	private String lastName;

	@NotNull(message =  "Gender should not be null")
	private String gender;

	@NotNull
//	@Max(value = 99999, message = "FlightId  cannot exceed 5 digits")
	private int flightId;

	@NotNull
//	@Max(value = 999999, message = "Flight number cannot exceed 6 digits")
	private double farePrice;
	
	private int pnrNo;
	



	public int getPnrNo() {
		return pnrNo;
	}
	public void setPnrNo(int pnrNo) {
		this.pnrNo = pnrNo;
	}
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getFlightId() {
		return flightId;
	}
	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}
	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}


	public double getFarePrice() {
		return farePrice;
	}

	public void setFarePrice(double farePrice) {
		this.farePrice = farePrice;
	}
}
