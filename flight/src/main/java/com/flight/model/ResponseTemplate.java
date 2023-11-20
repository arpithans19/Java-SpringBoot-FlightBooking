package com.flight.model;

import com.flight.entity.Flight;

public class ResponseTemplate {
	
	private Flight flight;
	private Fare fare;
	
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	public Fare getFare() {
		return fare;
	}
	public void setFare(Fare fare) {
		this.fare = fare;
	}
	
	

}
