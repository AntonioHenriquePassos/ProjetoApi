package com.antonio.Api.models;


import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="TB_CARD_TYPE")
public class CardType implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id; 
	
	@Column
	@NotBlank
	@Enumerated(EnumType.STRING)
	private Type typeOfCard;

	@OneToOne
	@JoinColumn(name="Card_Id")
	@JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
	private Cards card;
	
	
}