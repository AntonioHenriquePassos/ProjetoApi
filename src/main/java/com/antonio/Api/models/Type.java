package com.antonio.Api.models;

public enum Type {

	DEBIT_CARD ("DEBIT"), 
	CREDIT_CARD ("CREDIT"), 
	MEAL_CARD ("MEAL"), 
	GIFT_CARD ("GIFT");
	
	public String description;
	
	Type (String description){
		this.description = description;
	}
}
