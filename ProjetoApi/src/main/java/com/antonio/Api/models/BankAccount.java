package com.antonio.Api.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="TB_BANK_ACCOUNT")
public class BankAccount implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id; 
	
	@Column(length = 50)
	@NotBlank
	private String nameOwner;
	
	@Column(length = 4)
	@NotBlank
	private String agencyCode;
	
	@Column(length = 8)
	@NotBlank
	private String accountCode;
	
	@Column(length = 20)
	@NotBlank
	private String registerId;
	
	@Column(length = 1)
	@NotBlank
	private String verificationDigital;
	
	@OneToMany(mappedBy="bankAccount")
	public List<Cards> cards = new ArrayList<>();
	
	public UUID getId() {
		return id;
	}
	public String getNameOwner() {
		return nameOwner;
	}
	public String getAgencyCode() {
		return agencyCode;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public String getVerificationDigital() {
		return verificationDigital;
	}
	public List<Cards> getCards() {
		return cards;
	}
	public void setNameOwner(String nameOwner) {
		this.nameOwner = nameOwner;
	}
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public void setVerificationDigital(String verificationDigital) {
		this.verificationDigital = verificationDigital;
	}
	
	public String getRegisterID() {
		return registerId;
	}
	
	public void setRegisterID(String registerId) {
		this.registerId = registerId;
	}
	
	
	
	
	

}
 
