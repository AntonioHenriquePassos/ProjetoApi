package com.antonio.Api.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.antonio.Api.dtos.CardsDto;
import com.antonio.Api.models.BankAccount;
import com.antonio.Api.models.Cards;
import com.antonio.Api.services.BankAccountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/bank")
@Api(value = "ProjetoApi")
@CrossOrigin(origins = "*")
public class CardsController {

	public BankAccountService bankAccountService;

	public CardsController(BankAccountService bankAccountService) {
		this.bankAccountService = bankAccountService;

	}

	@PostMapping("/card/{id}")
	@ApiOperation(value = "Create a card which must be added to a bank account. "
			+ " The \"id\" references the Bank AccountÂ´s id to which the card will be added.")
	public ResponseEntity<Cards> createCard(@PathVariable(value = "id") Long id, @RequestBody @Valid CardsDto dtoCard) {
		BankAccount bankAccountFound = bankAccountService.findBankAccountId(id);
		Cards newCard = bankAccountService.AddCardToBankAccount(dtoCard, bankAccountFound);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCard.getId())
				.toUri();
		return ResponseEntity.created(uri).build();

	}

	@GetMapping("/card/{id}")
	@ApiOperation(value = "Retrieve a card by its id.")
	public ResponseEntity<Cards> findCardById(@PathVariable(value = "id") Long id) {
		Cards cardFound = bankAccountService.findCardById(id);
		return ResponseEntity.status(HttpStatus.OK).body(cardFound);

	}

	@PutMapping("/updateCard/{id}")
	@ApiOperation(value = "Update an existing card, found by its id. An already instantiated Card Type must be informed.")
	public ResponseEntity<Cards> updateCard(@PathVariable(value = "id") Long id, @RequestBody @Valid CardsDto cardDto) {
		Cards cardFound = bankAccountService.findCardById(id);
		return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.updateCard(cardFound, cardDto));
	}

	@DeleteMapping("/deleteCard/{id}")
	@ApiOperation(value = "Delete a card, found by its id.")
	public ResponseEntity<Object> deleteCardId(@PathVariable(value = "id") Long id) {
		bankAccountService.findCardById(id);
		return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.deleteCardId(id));
	}

}
