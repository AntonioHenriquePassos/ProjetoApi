package com.antonio.Api.dtos;

import java.math.BigDecimal;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.antonio.Api.models.CardFlag;
import com.antonio.Api.models.CardName;
import com.antonio.Api.models.Type;

public class CardsDto {

	@NotNull(message = "Description: Card Name = Filling cardName field out is mandatory.")
	@Enumerated(EnumType.STRING)
	private CardName cardName;

	@NotNull(message = "Description: Card Flag = Filling cardFlag field out is mandatory.")
	@Enumerated(EnumType.STRING)
	private CardFlag cardFlag;

	@NotBlank(message = "Description: Card Number = Filling cardNumber field out is mandatory.")
	@Size(max = 20, message = "Description: Card Number = Please, type up to a maximum of 20 characters.")
	private String cardNumber;

	@NotBlank(message = "Description: Card SecurityCode = Filling cardSecurityCode field out is mandatory.")
	@Size(max = 5, message = "Description: Card SecurityCode = Please, type up to a maximum of 5 characters.")
	private String cardSecurityCode;

	@NotNull(message = "Description: Card Limit = Filling cardLimit field out is mandatory.")
	@DecimalMax(value = "99999999999999999999", message = "Description: Card Limit = Please, type up to a maximum of 20 characters.")
	private BigDecimal cardLimit;

	@NotNull(message = "Description: Id of Card Type = Filling idtype field out is mandatory.")
	private Type type;

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

	public BigDecimal getCardLimit() {
		return cardLimit;
	}

	public void setCardLimit(BigDecimal cardLimit) {
		this.cardLimit = cardLimit;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public CardsDto(
			@NotNull(message = "Description: Card Name = Filling cardName field out is mandatory.") CardName cardName,
			@NotNull(message = "Description: Card Flag = Filling cardFlag field out is mandatory.") CardFlag cardFlag,
			@NotBlank(message = "Description: Card Number = Filling cardNumber field out is mandatory.") @Size(max = 20, message = "Description: Card Number = Please, type up to a maximum of 20 characters.") String cardNumber,
			@NotBlank(message = "Description: Card SecurityCode = Filling cardSecurityCode field out is mandatory.") @Size(max = 5, message = "Description: Card SecurityCode = Please, type up to a maximum of 5 characters.") String cardSecurityCode,
			@NotNull(message = "Description: Card Limit = Filling cardLimit field out is mandatory.") @DecimalMax(value = "99999999999999999999", message = "Description: Card Limit = Please, type up to a maximum of 20 characters.") BigDecimal cardLimit,
			@NotNull(message = "Description: Id of Card Type = Filling idtype field out is mandatory.") @Max(value = 6, message = "Description: Id of Card Type = Please, type only 1 character.") Type type) {
		this.cardName = cardName;
		this.cardFlag = cardFlag;
		this.cardNumber = cardNumber;
		this.cardSecurityCode = cardSecurityCode;
		this.cardLimit = cardLimit;
		this.type = type;
	}

	public CardsDto() {
	}

}
