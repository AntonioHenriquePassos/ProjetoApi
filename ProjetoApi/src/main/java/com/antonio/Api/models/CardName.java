package com.antonio.Api.models;

public enum CardName {

	VIP("VIP"),
	SUPER_PRIME("SUPER PRIME"),
	PRIME ("PRIME"),
	STANDARD("STANDARD");
	
	public String description;
	
	CardName(String description){
		this.description=description;
	}

}
