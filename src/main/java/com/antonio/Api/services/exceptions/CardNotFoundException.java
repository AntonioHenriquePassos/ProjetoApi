package com.antonio.Api.services.exceptions;

public class CardNotFoundException extends RuntimeException {
private static final long serialVersionUID = 1L;
	
	public CardNotFoundException(String msg) {
		super(msg);
	}

}
