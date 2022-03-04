package com.antonio.Api.models;

public enum Type {

	DEBIT_CARD ("Debit Card"), 
	CREDIT_CARD ("Credit Card"), 
	MEAL_CARD ("Meal Card"), 
	GIFT_CARD ("Gift Card");
	
	public String description;
	
	Type (String description){
		this.description = description;
	}
}
