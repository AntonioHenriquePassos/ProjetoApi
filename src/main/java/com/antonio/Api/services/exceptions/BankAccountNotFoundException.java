package com.antonio.Api.services.exceptions;

public class BankAccountNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BankAccountNotFoundException(String msg) {
		super(msg);
	}

}
