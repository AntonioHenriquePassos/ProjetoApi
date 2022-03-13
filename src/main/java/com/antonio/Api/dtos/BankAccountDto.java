package com.antonio.Api.dtos;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;



public class BankAccountDto {
	
	@NotBlank(message="Name Owner = Filling nameOwner field out is mandatory")
	@Size(max=50, message="Name Owner = Please, type up to a maximum of 50 characters.")
	private String nameOwner;
	
	@NotBlank(message="Agency Code = Filling agencyCode field out is mandatory")
	@Size(max=4, message="Agency Code = Please, type up to a maximum of 4 characters.")
	private String agencyCode;
	
	@NotBlank(message="Account Code = Filling accountCode field out is mandatory")
	@Size(max=8, message="Account Code = Please, type up to a maximum of 8 characters.")
	private String accountCode;
	
	@NotBlank(message="Register Id = Filling registerId field out is mandatory")
	@Size(max=20, message="Register Id = Please, type up to a maximum of 20 characters.")
	private String registerId;
	
	@NotBlank(message="Verification Digital = Filling verificationDigital field out is mandatory")
	@Size(max=1, message="Verification Digital = Please, type only 1 character.")
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
