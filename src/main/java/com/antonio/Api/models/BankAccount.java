package com.antonio.Api.models;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.*;



@Entity
@Table(name="TB_BANK_ACCOUNT")
public class BankAccount implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(length = 50)
	private String nameOwner;
	
	@Column(length = 4)
		private String agencyCode;
	
	@Column(length = 8, unique=true)
	private String accountCode;
	
	@Column(length = 20)
	private String registerId;
	
	@Column(length = 1)
	private String verificationDigital;
	
	@OneToMany(mappedBy="bankAccount", orphanRemoval=true, cascade=CascadeType.PERSIST  )
	private List<Cards> cards = new ArrayList<>();
	
	

	public Long getId() {
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
	
	public void setCards(List<Cards> cards) {
		this.cards = cards;
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
	
	public String getRegisterId() {
		return registerId;
	}
	
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
	
	
	

}
 
