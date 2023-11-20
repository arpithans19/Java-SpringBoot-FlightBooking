package com.user.service;

import java.util.Objects;
import java.util.Optional;

import com.user.entity.DBSequence;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.user.entity.User;
import com.user.exception.PhoneNumberAlreadyExistingException;
import com.user.exception.UserNameAlreadyExistingException;
import com.user.exception.UserNotFoundException;
import com.user.repository.UserRepository;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MongoOperations mongoOperations;
	
	Logger logger=org.slf4j.LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public User saveUser(User user) {
		user.setUserId(getSequenceNumber(User.SEQUENCE_NAME));
		Optional<User>optionalUser=userRepository.findByUserName(user.getUserName());
			logger.warn("adding user");
		
		if(optionalUser.isPresent()) {
			throw new UserNameAlreadyExistingException("Username already exists with name "+user.getUserName());
		}
		Optional<User> userByPhone = userRepository.findByPhone(user.getMobileNumber());
		if (userByPhone.isPresent()) {
			throw new PhoneNumberAlreadyExistingException("Phone number already exists " + user.getMobileNumber());
		}
		return userRepository.save(user);
	}
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
	public User getUser(int userId) {
		Optional<User> optionalUser= userRepository.findById(userId);
		if(optionalUser.isEmpty()) {
			throw new UserNotFoundException("User not found with Id: "+userId);
		}
			return optionalUser.get();
	}

	@Override
	public void deleteUser(int userId) {
		Optional<User> optionalUser=userRepository.findById(userId);
		if(optionalUser.isEmpty()) {
			logger.warn("This is an warning message for delete user by Id");
			logger.trace("user with this id");
			throw new UserNotFoundException("User not found with Id: "+userId);
		}
		userRepository.deleteById(userId);		
	}

	@Override
	public User userByName(String userName) {
		Optional<User> optionalUser= userRepository.findByName(userName);

		if(optionalUser.isPresent()) {
			logger.warn("This is an warning message for get user by Id");
		}
		if(optionalUser.isEmpty()) {
			throw new UserNotFoundException("User not found with Id: "+userName);
		}
		return optionalUser.get();
	}

	@Override
	public User modifyUser(User user) {
		Optional<User> optionalUser= userRepository.findById(user.getUserId());
		if(optionalUser.isEmpty()) {
			throw new UserNotFoundException("User not found with Id: "+user.getUserId());
		}
		return userRepository.save(user);
	}

}
