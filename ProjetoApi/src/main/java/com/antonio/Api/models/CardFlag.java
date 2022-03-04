package com.antonio.Api.models;

public enum CardFlag {
	
	MASTERCARD("Mastercard"),
	VISA("Visa"),
	ELO("Elo");
	
	public String type;
	
	CardFlag (String type){
		this.type=type;
	}

}
