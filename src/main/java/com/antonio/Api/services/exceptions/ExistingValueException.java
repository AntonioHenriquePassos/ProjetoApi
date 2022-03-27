package com.antonio.Api.services.exceptions;

public class ExistingValueException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExistingValueException(String msg) {
		msg = "This value already exists in the database.";
	}

}
