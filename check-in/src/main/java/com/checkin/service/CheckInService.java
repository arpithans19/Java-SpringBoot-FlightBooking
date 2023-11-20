package com.checkin.service;

import com.checkin.entity.CheckIn;

import java.util.List;

public interface CheckInService {

	public CheckIn saveCheckIn(CheckIn checkIn);

//	public CheckInResponse getCheckInById(int checkInId);

	CheckIn getCheckInById(int checkInId);

	public int getSequenceNumber(String sequenceName);

	public void deleteCheckinById(int checkInId);

	public List<CheckIn> getAllCheckIn();

//	CheckIn getByCheckInByPnrNo (long pnrNo);


}
