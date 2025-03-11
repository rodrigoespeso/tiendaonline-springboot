package com.dempo.exception;

public class UserNotFoundException extends ServiceException {

	private static final long serialVersionUID = 6460816038074681177L;

	public UserNotFoundException(String message) {
        super(message);
    }
}
