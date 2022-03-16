package com.antonio.Api.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="TB_CARD_TYPE")
public class CardType implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id; 
	
	@Column (unique=true)
	@Enumerated(EnumType.STRING)
	private Type typeOfCard;

	@OneToMany ( mappedBy="cardType", orphanRemoval = true, cascade=CascadeType.PERSIST)
	@JsonManagedReference
	@JsonProperty(access= JsonProperty.Access.WRITE_ONLY )
	private List <Cards> cards = new ArrayList<>();

	public List<Cards> getCard() {
		return cards;
	}

	public void setCard(List<Cards> cards) {
		this.cards = cards;
	}

	public Type getTypeOfCard() {
		return typeOfCard;
	}

	public void setTypeOfCard(Type typeOfCard) {
		this.typeOfCard = typeOfCard;
	}

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	
	
}