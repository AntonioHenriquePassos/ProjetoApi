package com.antonio.Api.dtos;

import javax.validation.constraints.NotNull;

import com.antonio.Api.models.Type;

public class CardTypeDto {
	
	@NotNull
	private Type typeOfCard;

	public Type getTypeOfCard() {
		return typeOfCard;
	}

	public void setTypeOfCard(Type typeOfCard) {
		this.typeOfCard = typeOfCard;
	}

	public CardTypeDto(@NotNull Type typeOfCard) {
		this.typeOfCard = typeOfCard;
	}

	public CardTypeDto() {
	}
	
	
	
}
