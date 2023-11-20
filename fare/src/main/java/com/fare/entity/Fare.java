package com.fare.entity;



import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "fare")
public class Fare {

	@Transient
	public static final String SEQUENCE_NAME="fare_sequence";


	@Id
	private int fareId;
	private String fareType;
	private double farePrice;

	public int getFareId() {
		return fareId;
	}

	public void setFareId(int fareId) {
		this.fareId = fareId;
	}

	public String getFareType() {
		return fareType;
	}

	public void setFareType(String fareType) {
		this.fareType = fareType;
	}

	public double getFarePrice() {
		return farePrice;
	}

	public void setFarePrice(double farePrice) {
		this.farePrice = farePrice;
	}

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
}
