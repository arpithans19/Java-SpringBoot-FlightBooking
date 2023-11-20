package com.checkin.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.processing.Generated;
import java.lang.reflect.Type;


@Document(collection = "checkin")
public class CheckIn {

	@Transient
	public static final String SEQUENCE_NAME="Checkin_sequence";

	@Id
	private int checkInId;
	private String status;
	private int seatNo;
//	private long pnrNo;
//	private int flightId;
	
	public int getCheckInId() {
		return checkInId;
	}
	public void setCheckInId(int checkInId) {
		this.checkInId = checkInId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getSeatNo() { return seatNo; }
	public void setSeatNo(int seatNo) {	this.seatNo = seatNo; }
	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
//	public int getFlightId() { return flightId; }
//	public void setFlightId(int flightId) {	this.flightId = flightId; }
	
}
