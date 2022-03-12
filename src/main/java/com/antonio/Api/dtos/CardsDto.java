package com.antonio.Api.dtos;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.antonio.Api.models.CardFlag;
import com.antonio.Api.models.CardName;
import com.antonio.Api.models.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CardsDto {
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private CardName cardName;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private CardFlag cardFlag;
	
	@NotBlank
	private String cardNumber;
	
	@NotBlank (message = "Campo não pode ser nulo ou vazio.")
	private String cardSecurityCode;
	
	@NotNull
	private Double cardLimit;
	
	@NotNull
	private Integer idtype; //Id of the CardType object, e.g: GIFT_CARD = 1.
	
		
	public CardName getCardName() {
		return cardName;
	}

	public void setCardName(CardName cardName) {
		this.cardName = cardName;
	}

	public CardFlag getCardFlag() {
		return cardFlag;
	}

	public void setCardFlag(CardFlag cardFlag) {
		this.cardFlag = cardFlag;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardSecurityCode() {
		return cardSecurityCode;
	}

	public void setCardSecurityCode(String cardSecurityCode) {
		this.cardSecurityCode = cardSecurityCode;
	}

	public Double getCardLimit() {
		return cardLimit;
	}

	public void setCardLimit(Double cardLimit) {
		this.cardLimit = cardLimit;
	}
	
	public Integer getidetype() {
		return idtype;
	}
	
	public void setidtype(Integer idtype) {
		this.idtype = idtype;
	}

	
	

	

}
