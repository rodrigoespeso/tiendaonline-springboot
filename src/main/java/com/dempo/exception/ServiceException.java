package com.dempo.exception;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 7120021428092859101L;

	public ServiceException(String message) {
        super(message);
    }
}
