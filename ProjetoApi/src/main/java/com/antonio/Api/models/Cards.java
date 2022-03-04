package com.antonio.Api.models;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name="TB_CARDS")
public class Cards implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id; 
	
	@Column
	@NotBlank
	@Enumerated(EnumType.STRING)
	private CardName cardName;
	
	@Column
	@NotBlank
	@Enumerated(EnumType.STRING)
	private CardFlag cardFlag;
	
	@Column (length=20)
	@NotBlank
	private String cardNumber;
	
	@Column(length=5)
	@NotBlank
	private String cardSecurityCode;
	
	@Column(length=20)
	@NotBlank
	private Double cardLimit;
	
	
	@OneToOne(mappedBy="card")
	public CardType cardType;
	
	@ManyToOne
	@JoinColumn(name="Bank_Account_Id")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private BankAccount bankAccount;
	
	public UUID getId() {
		return id;
	}
	
	
	
	public String getCardName() {
		return cardName;
	}
	public String getCardFlag() {
		return cardFlag;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public String getCardSecurityCode() {
		return cardSecurityCode;
	}
	public Double getCardLimit() {
		return cardLimit;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public void setCardFlag(String cardFlag) {
		this.cardFlag = cardFlag;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public void setCardSecurityCode(String cardSecurityCode) {
		this.cardSecurityCode = cardSecurityCode;
	}
	public void setCardLimit(Double cardLimit) {
		this.cardLimit = cardLimit;
	}
	public CardType getCardType() {
		return cardType;
	}
	
	
	
	
}