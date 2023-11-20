package com.user.exception;

@SuppressWarnings("serial")
public class AuthenticationFailedException extends RuntimeException {

	public AuthenticationFailedException(String message) {
		super(message);
	}

}
