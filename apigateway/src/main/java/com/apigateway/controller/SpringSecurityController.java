package com.apigateway.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.apigateway.model.AuthenticationRequest;
import com.apigateway.model.AuthenticationResponse;
import com.apigateway.model.Fare;
import com.apigateway.model.Flight;
import com.apigateway.model.LoginResponse;
import com.apigateway.model.User;
import com.apigateway.service.MyUserDetailsService;
import com.apigateway.util.JwtUtil;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SpringSecurityController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/hello")
	public String firstPage() {
		return "Hello World";
	}

	@GetMapping("/user/find/{userId}")
	public User first2Page(@PathVariable int userId) {
		return restTemplate.getForObject("http://user-service/user/find/" + userId, User.class);
	}

	@GetMapping("/user/{userName}")
	public User first2Page(@PathVariable String userName) {
		return restTemplate.getForObject("http://user-service/user/" + userName, User.class);
	}

	@GetMapping("/flight/get/{flightId}")
	public Flight fetchByFlightId(@PathVariable int flightId) {
		return restTemplate.getForObject("http://flight-service/flight/get/" + flightId, Flight.class);
	}

	@GetMapping("/flight/getallflights")
	public Flight[] fetchAllFlights() {
		return restTemplate.getForObject("http://flight-service/flight/getallflights", Flight[].class);

	}

	@GetMapping("/flight/{fromLocation}/{destination}/{date}")
	public Flight[] fetchFlights(@PathVariable("fromLocation") String fromLocation,
			@PathVariable("destination") String destination,
			@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

		return restTemplate.getForObject(
				"http://flight-service/flight/" + fromLocation + "/" + destination + "/" + date, Flight[].class);

	}

	@PostMapping("/flight/addFlight")
	public Flight saveFlights(@RequestBody Flight flight) {
		return restTemplate.postForObject("http://flight-service/flight/addFlight", flight, Flight.class);

	}

	@GetMapping("/fare/find/{fareId}")
	public Fare fetchByFareId(@PathVariable int fareId) {
		return restTemplate.getForObject("http://fare-service/fare/find/" + fareId, Fare.class);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));

			final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
			LoginResponse loginResponse = restTemplate.getForObject(
					"http://user-service/user/" + authenticationRequest.getUsername(), LoginResponse.class);
			final String jwt = jwtTokenUtil.generateToken(userDetails);
			AuthenticationResponse authenticationResponse = new AuthenticationResponse();
			authenticationResponse.setUserId(loginResponse.getUserId());
			authenticationResponse.setUserRole(loginResponse.getUserRole());
			authenticationResponse.setJwt(jwt);
			return ResponseEntity.ok(authenticationResponse);
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

	}
//	@GetMapping("/validate")
//    public AuthenticationResponse getValidity(@RequestHeader(name="Authorization")  String jwt) {
////       byte[] byt = Base64.getUrlDecoder().decode(token);
//       
//       return validateService.validate(jwt);
//    }
}
