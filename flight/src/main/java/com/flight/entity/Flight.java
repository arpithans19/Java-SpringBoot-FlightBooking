package com.flight.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Document(collection = "flight")
public class Flight {

	@Transient
	public static final String SEQUENCE_NAME="flight_sequence";

	@Id
	private int flightId;
	@NotEmpty(message = "fromLocation should not be null")
	private String fromLocation;
	private String destination;
	private LocalDate date;
	private String flightName;
	private String flightType;
	private double farePrice;
	
	public Flight(int flightId, String fromLocation, String destination, LocalDate date, String flightName,
			String flightType, double farePrice) {
		super();
		this.flightId = flightId;
		this.fromLocation = fromLocation;
		this.destination = destination;
		this.date = date;
		this.flightName = flightName;
		this.flightType = flightType;
		this.farePrice = farePrice;
	}

	public Flight() {
		super();
	}




	public String getFlightType() {
		return flightType;
	}

	public double getFarePrice() {
		return farePrice;
	}

	public void setFarePrice(double farePrice) {
		this.farePrice = farePrice;
	}

	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}
	public int getFlightId() {
		return flightId;
	}
	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}
	public String getFromLocation() {
		return fromLocation;
	}
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getFlightName() {
		return flightName;
	}

	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
}
