package com.antonio.Api.models;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name="TB_CARDS")
public class Cards implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id; 
	
	@Column
	@Enumerated(EnumType.STRING)
	private CardName cardName;
	
	@Column
	@Enumerated(EnumType.STRING)
	private CardFlag cardFlag;
	
	@Column (length=20, unique=true)
	private String cardNumber;
	
	@Column(length=5)
	private String cardSecurityCode;
	
	@Column(length=20)
	private BigDecimal cardLimit;
	
	
	@ManyToOne( cascade=CascadeType.PERSIST)
	@JsonBackReference
	@JoinColumn(name="Card_Type_Id")
	private CardType cardType;
	
	//In SQL, the field "type" shows the number of the respective ENUM type.
	private Type type;
	
	 
	
	@ManyToOne( cascade=CascadeType.PERSIST)
	@JoinColumn(name="Bank_Account_Id")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private BankAccount bankAccount;
	
	public Long getId() {
		return id;
	}
	
		public CardName getCardName() {
		return cardName;
	}
	public CardFlag getCardFlag() {
		return cardFlag;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public String getCardSecurityCode() {
		return cardSecurityCode;
	}
	public BigDecimal getCardLimit() {
		return cardLimit;
	}
	public void setCardName( CardName cardName) {
		this.cardName = cardName;
	}
	public void setCardFlag( CardFlag cardFlag) {
		this.cardFlag = cardFlag;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public void setCardSecurityCode(String cardSecurityCode) {
		this.cardSecurityCode = cardSecurityCode;
	}
	public void setCardLimit(BigDecimal cardLimit) {
		this.cardLimit = cardLimit;
	}


	public CardType getCardType() {
		return cardType;
	}


	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}


	public BankAccount getBankAccount() {
		return bankAccount;
	}


	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}



	public void setId(Long id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	
	
	
	
}