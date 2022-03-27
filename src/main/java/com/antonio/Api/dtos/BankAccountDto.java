package com.antonio.Api.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class BankAccountDto {

	@NotBlank(message = "Description: Name Owner = Filling nameOwner field out is mandatory.")
	@Size(max = 50, message = "Description: Name Owner = Please, type up to a maximum of 50 characters.")
	private String nameOwner;

	@NotBlank(message = "Description: Agency Code = Filling agencyCode field out is mandatory.")
	@Size(max = 4, message = "Description: Agency Code = Please, type up to a maximum of 4 characters.")
	private String agencyCode;

	@NotBlank(message = "Description: Account Code = Filling accountCode field out is mandatory.")
	@Size(max = 8, message = "Description: Account Code = Please, type up to a maximum of 8 characters.")
	private String accountCode;

	@NotBlank(message = "Description: Register Id = Filling registerId field out is mandatory.")
	@Size(max = 20, message = "Description: Register Id = Please, type up to a maximum of 20 characters.")
	private String registerId;

	@NotBlank(message = "Description: Verification Digital = Filling verificationDigital field out is mandatory.")
	@Size(max = 1, message = "Description: Verification Digital = Please, type only 1 character.")
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

	public BankAccountDto(
			@NotBlank(message = "Description: Name Owner = Filling nameOwner field out is mandatory.") @Size(max = 50, message = "Description: Name Owner = Please, type up to a maximum of 50 characters.") String nameOwner,
			@NotBlank(message = "Description: Agency Code = Filling agencyCode field out is mandatory.") @Size(max = 4, message = "Description: Agency Code = Please, type up to a maximum of 4 characters.") String agencyCode,
			@NotBlank(message = "Description: Account Code = Filling accountCode field out is mandatory.") @Size(max = 8, message = "Description: Account Code = Please, type up to a maximum of 8 characters.") String accountCode,
			@NotBlank(message = "Description: Register Id = Filling registerId field out is mandatory.") @Size(max = 20, message = "Description: Register Id = Please, type up to a maximum of 20 characters.") String registerId,
			@NotBlank(message = "Description: Verification Digital = Filling verificationDigital field out is mandatory.") @Size(max = 1, message = "Description: Verification Digital = Please, type only 1 character.") String verificationDigital,
			List<@Valid CardsDto> cards) {
		this.nameOwner = nameOwner;
		this.agencyCode = agencyCode;
		this.accountCode = accountCode;
		this.registerId = registerId;
		this.verificationDigital = verificationDigital;
		this.cards = cards;
	}

	// Constructor without Cards
	public BankAccountDto(
			@NotBlank(message = "Description: Name Owner = Filling nameOwner field out is mandatory.") @Size(max = 50, message = "Description: Name Owner = Please, type up to a maximum of 50 characters.") String nameOwner,
			@NotBlank(message = "Description: Agency Code = Filling agencyCode field out is mandatory.") @Size(max = 4, message = "Description: Agency Code = Please, type up to a maximum of 4 characters.") String agencyCode,
			@NotBlank(message = "Description: Account Code = Filling accountCode field out is mandatory.") @Size(max = 8, message = "Description: Account Code = Please, type up to a maximum of 8 characters.") String accountCode,
			@NotBlank(message = "Description: Register Id = Filling registerId field out is mandatory.") @Size(max = 20, message = "Description: Register Id = Please, type up to a maximum of 20 characters.") String registerId,
			@NotBlank(message = "Description: Verification Digital = Filling verificationDigital field out is mandatory.") @Size(max = 1, message = "Description: Verification Digital = Please, type only 1 character.") String verificationDigital) {
		this.nameOwner = nameOwner;
		this.agencyCode = agencyCode;
		this.accountCode = accountCode;
		this.registerId = registerId;
		this.verificationDigital = verificationDigital;
	}

	public BankAccountDto() {
	}

}
