package com.antonio.Api.dtos;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;



public class BankAccountDto {
	
	@NotBlank
	private String nameOwner;
	
	@NotBlank
	private String agencyCode;
	
	@NotBlank
	private String accountCode;
	
	@NotBlank
	private String registerId;
	
	@NotBlank
	private String verificationDigital;
	
	public List<@Valid CardsDto> cards = new ArrayList<>();

	public String getNameOwner() {
		return nameOwner;
	}

	public void setNameOwner(String nameOwner) {
		this.nameOwner = nameOwner;
	}

	public String getAgencyCode() {
		return agencyCode;
	}

	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getVerificationDigital() {
		return verificationDigital;
	}

	public void setVerificationDigital(String verificationDigital) {
		this.verificationDigital = verificationDigital;
	}

	public List<CardsDto> getCards() {
		return cards;
	}

	public void setCards(List<CardsDto> cards) {
		this.cards = cards;
	}

}
