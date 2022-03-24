package com.antonio.Api.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antonio.Api.dtos.CardTypeDto;
import com.antonio.Api.models.CardType;
import com.antonio.Api.services.BankAccountService;

import io.swagger.annotations.*;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/bank")
@Api(value="ProjetoApi")
@CrossOrigin(origins="*")
public class CardTypeController {
	
	public BankAccountService bankAccountService;

	
	
	public CardTypeController(BankAccountService bankAccountService) {
			this.bankAccountService = bankAccountService;
	}
	
		
	
	
	@PostMapping("/cardType/")
	@ApiOperation(value="Create a card type.")
	public ResponseEntity<Object> createCardType(@RequestBody CardTypeDto cardTypeDto){
		return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.createCardType(cardTypeDto));

	}
	
	
	@GetMapping("/cardtype/{id}")
	@ApiOperation(value="Retrieve a card type, found by its id.")
	public ResponseEntity<Object> findCardTypeById(@PathVariable(value = "id") Integer id){
		CardType cardType= bankAccountService.findCardTypeId(id);
		return ResponseEntity.status(HttpStatus.OK).body(cardType);
	}
	

			
	@DeleteMapping("/deleteCardType/{id}")
	@ApiOperation(value="Delete a card type, found by its id."
			+ " The deletion accurs only if there are no cards related to the card type.")	
	public ResponseEntity<Object> deleteCardTypeId(@PathVariable(value="id") Integer id){
		CardType cardTypeFound = bankAccountService.findCardTypeId(id);
		if (cardTypeFound.getCard().isEmpty()) {
			bankAccountService.deleteCardTypeId(id);
	        return ResponseEntity.status(HttpStatus.OK).body("Card type deleted successfully.");
		} else {			
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, cannot delete card type with cards in it.");
		}
		
	}



}
