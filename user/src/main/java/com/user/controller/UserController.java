package com.user.controller;

import com.user.entity.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.user.entity.User;
import com.user.service.UserService;



@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	@Autowired
	private UserService userService;
	

	@PostMapping("/save")
	public ResponseEntity<User> addUser(@RequestBody User user) {
		User newUser = userService.saveUser(user);
		ResponseEntity<User> responseEntity = new ResponseEntity<>(newUser, HttpStatus.OK);
		return responseEntity;

	}
	
	//@HystrixCommand(fallbackMethod = "getPassenger")
	@GetMapping("/find/{userId}")
	public ResponseEntity<Object> getUser(@PathVariable("userId") int userId) {
		ResponseEntity<Object> responseEntity = null;
		User user = userService.getUser(userId);
		responseEntity = new ResponseEntity<Object>(user, HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping("/{userName}")
	public ResponseEntity<Object> getUserName(@PathVariable("userName") String userName) {
		ResponseEntity<Object> responseEntity = null;
		User user = userService.userByName(userName);
		LoginResponse loginResponse=new LoginResponse();
		loginResponse.setUserId(user.getUserId());
		loginResponse.setUserName(user.getUserName());
		loginResponse.setPassword(user.getPassword());
		loginResponse.setUserRole(user.getUserRole());
		responseEntity=new ResponseEntity<>(user,HttpStatus.OK);
		return  responseEntity;
	}
	
	@PutMapping("/modify")
	public ResponseEntity<User> modifyUser(@RequestBody User user) {
		User modifiedUser=userService.modifyUser(user);
		ResponseEntity<User> responseEntity = new ResponseEntity<>(modifiedUser, HttpStatus.OK);
		return responseEntity;
	}
	
	@DeleteMapping("delete/{userId}")
	public ResponseEntity<String> removeUser(@PathVariable("userId") int userId) {

		userService.deleteUser(userId);
		return new ResponseEntity<>("Passenger Deleted Successfully.", HttpStatus.OK);
	}

}
