package com.antonio.Api.services.exceptions;

public class CardTypeNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CardTypeNotFoundException(String msg) {
		super(msg);
	}

}
