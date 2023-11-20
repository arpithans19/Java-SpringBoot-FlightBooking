package com.apigateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.apigateway.exception.AuthenticationFailedException;
import com.apigateway.model.AuthenticationResponse;
import com.apigateway.model.LoginResponse;
import com.apigateway.util.JwtUtil;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	JwtUtil jwtutils;
   
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		LoginResponse loginResponse = restTemplate.getForObject("http://user-service/user/" + username,
				LoginResponse.class);
		
		GrantedAuthority auth = new SimpleGrantedAuthority(loginResponse.getUserRole());
		
		ArrayList<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		
		list.add(auth);

		UserDetails userDetails = new User(loginResponse.getUserName(), loginResponse.getPassword(), list);
		
		if (userDetails == null) {
			throw new AuthenticationFailedException("Username or password Invalid!!");
		}

		return userDetails;
	}
	
//	public AuthenticationResponse validate(String token) {
//		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
//
//
//		String jwt = token.substring(7);
//
//		authenticationResponse.setJwt(jwt);
//		authenticationResponse.setValid(jwtutils.validateToken(jwt));
//		return authenticationResponse;
//	}
}