package com.checkin.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import com.checkin.entity.DBSequence;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.checkin.entity.CheckIn;
import com.checkin.exception.CheckInNotFoundException;
import com.checkin.repository.CheckInRepository;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;


@Service
public class CheckInServiceImpl implements CheckInService{

	@Autowired
	private CheckInRepository checkInRepository;
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MongoOperations mongoOperations;
	
	Logger logger=org.slf4j.LoggerFactory.getLogger(CheckInServiceImpl.class);

	@Override
	public CheckIn saveCheckIn(CheckIn checkIn) {
		
		Random random=new Random();
//		checkIn.setPnrNo(random.nextInt(99999999)+100000);
		checkIn.setSeatNo(random.nextInt(60)+10);
		
//		if(checkIn.setSeatNo()==checkIn.getSeatNo())
		checkIn.setCheckInId(getSequenceNumber(CheckIn.SEQUENCE_NAME));
		return checkInRepository.save(checkIn);
	}

	@Override
	public CheckIn getCheckInById(int checkInId) {
		Optional<CheckIn> optionalFetchById=checkInRepository.findById(checkInId);
		logger.warn("get checkin by Id");
		if(optionalFetchById.isEmpty()) {
			throw new CheckInNotFoundException("Checkin not found with Id: "+ checkInId);
		}
		return optionalFetchById.get();
	}

//	@Override
//	public CheckInResponse getCheckInByFlightId(int checkInId) {
//		CheckInResponse response= new CheckInResponse();
//		Optional<CheckIn> optionalCheckIn=checkInRepository.findById(checkInId);
//		logger.warn("get checkin by flightId");
//		if (optionalCheckIn.isEmpty()) {
//			throw new CheckInNotFoundException("Flight Not found with checkInId: " + checkInId);
//		}
//		CheckIn checkIn=optionalCheckIn.get();
//		Flight flight=restTemplate.getForObject("http://flight-service/flight/get/"+checkIn.getFlightId(), Flight.class);
//		response.setFlight(flight);
//		response.setCheckIn(checkIn);
//
//		return response;
//	}

	@Override
	public int getSequenceNumber(String sequenceName) {
		//generate sequence no
		Query query=new Query(Criteria.where("id").is(sequenceName));
		//update the sequence no
		Update update=new Update().inc("seq",1);
		//modify in document
		DBSequence counter=mongoOperations.findAndModify(query,update, options().returnNew(true).upsert(true),DBSequence.class);
		return !Objects.isNull(counter)? counter.getSeq():1;
	}

	@Override
	public void deleteCheckinById(int checkInId) {
		Optional<CheckIn> optionalCheckIn = checkInRepository.findById(checkInId);
		if (optionalCheckIn.isEmpty()) {
			throw new CheckInNotFoundException("CheckIn Not existing with Id:" + checkInId);
		}
		checkInRepository.deleteById(checkInId);

	}

	@Override
	public List<CheckIn> getAllCheckIn() {
		List<CheckIn> checkIn = checkInRepository.findAll();
		if (checkIn.isEmpty()) {
			throw new CheckInNotFoundException("CheckIn Not available");
		}
		return checkIn;
	}
//
//	@Override
//	public CheckIn getByCheckInByPnrNo(long pnrNo) {
//		Optional<CheckIn> optionalFetchById= Optional.ofNullable(checkInRepository.findByPnr(pnrNo));
//		if(optionalFetchById.isEmpty()) {
//			throw new CheckInNotFoundException("CheckIn not found with Id: "+ pnrNo);
//		}
//		return optionalFetchById.get();
//	}

}
