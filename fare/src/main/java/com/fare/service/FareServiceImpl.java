package com.fare.service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.fare.entity.DBSequence;
import com.fare.entity.Fare;
import com.fare.exception.FareNotFoundException;
import com.fare.repository.FareRepository;

@Service
public class FareServiceImpl implements FareService{

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private FareRepository fareRepository;
	
	Logger logger=org.slf4j.LoggerFactory.getLogger(FareServiceImpl.class);

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
	public Fare saveFare(Fare fare) {
		fare.setFareId(getSequenceNumber(Fare.SEQUENCE_NAME));
		return fareRepository.save(fare);
	}

	@Override
	public Fare getFareById(int fareId) {
		Optional<Fare> optionalFetchById=fareRepository.findById(fareId);
		logger.warn("get fare by Id");
		if(optionalFetchById.isEmpty()) {
			throw new FareNotFoundException("Fare not found with Id: "+ fareId);
		}
		return optionalFetchById.get();
	}

	@Override
	public Fare modifyFare(Fare fare) {
		Optional<Fare> optionalModifyById=fareRepository.findById(fare.getFareId());
		if(optionalModifyById.isPresent()) {
			logger.warn("modify fare by Id");
		return fareRepository.save(fare);
		}
		else {
			throw new FareNotFoundException("Fare not found with Id: "+ fare.getFareId() );
		}
	}

	@Override
	public void deleteFare(int fareId) {
		Optional<Fare> optionalFare=fareRepository.findById(fareId);
		if(optionalFare.isPresent()) {
			logger.warn("delete fare by Id");
		fareRepository.deleteById(fareId);
		}
		else {
			throw new FareNotFoundException("Fare not found with Id: "+ fareId );
		}
		
	}

	@Override
	public List<Fare> getAllFares() {

		List<Fare> fare = fareRepository.findAll();
		if (fare.isEmpty()) {
			throw new FareNotFoundException("Fare Not available");
		}
		return fare;
	}

	@Override
	public Fare getFareByPrice(double farePrice) {
		Optional<Fare> optionalFetchByPrice=fareRepository.findByPrice(farePrice);
		logger.warn("get fare by price");
		return optionalFetchByPrice.get();
	}

}
