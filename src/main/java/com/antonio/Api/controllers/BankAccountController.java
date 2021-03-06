package com.antonio.Api.controllers;

import java.net.URI;
import java.util.List;

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

import com.antonio.Api.dtos.BankAccountDto;
import com.antonio.Api.models.BankAccount;

import com.antonio.Api.services.BankAccountService;

import io.swagger.annotations.*;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/bank")
@Api(value = "ProjetoApi")
@CrossOrigin(origins = "*")
public class BankAccountController {

	public BankAccountService bankAccountService;

	public BankAccountController(BankAccountService bankAccountService) {
		this.bankAccountService = bankAccountService;

	}

	@PostMapping
	@ApiOperation(value = "Create a bank account with cards or not.")
	public ResponseEntity<BankAccount> createBankAccount(@RequestBody @Valid BankAccountDto dto) {
		BankAccount newBankAccount = bankAccountService.createBankAccount(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newBankAccount.getId())
				.toUri();
		return ResponseEntity.created(uri).build();

	}

	@GetMapping
	@ApiOperation(value = "Retrieve a list with all bank accounts containing their respective cards.")
	public ResponseEntity<List<BankAccount>> listAllBankAccountsWithCards() {
		return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.findAll());

	}

	@GetMapping("/bankAccount/{id}")
	@ApiOperation(value = "Retrieve a bank acount by its id.")
	public ResponseEntity<BankAccount> findAccountById(@PathVariable(value = "id") Long id) {
		BankAccount bankAccountFound = bankAccountService.findBankAccountId(id);
		return ResponseEntity.status(HttpStatus.OK).body(bankAccountFound);

	}

	@PutMapping("/updateBankAccount/{id}")
	@ApiOperation(value = "Update only the bank account information,"
			+ " cards will be updated with their proper function.")
	public ResponseEntity<BankAccount> updateBankAccount(@PathVariable(value = "id") Long id,
			@RequestBody @Valid BankAccountDto bankAccountDto) {
		BankAccount BankAccountFound = bankAccountService.findBankAccountId(id);
		return ResponseEntity.status(HttpStatus.OK)
				.body(bankAccountService.updateBankAccount(BankAccountFound, bankAccountDto));

	}

	@DeleteMapping("/deleteBankAccount/{id}")
	@ApiOperation(value = "Delete a bank account, found by its id."
			+ " The deletion accurs only if there are no cards related to the bank account.")
	public ResponseEntity<Object> deleteBankAccount(@PathVariable(value = "id") Long id) {
		BankAccount bankAccountFound = bankAccountService.findBankAccountId(id);
		if (bankAccountFound.getCards().isEmpty()) {
			bankAccountService.deleteBankAccountId(id);
			return ResponseEntity.status(HttpStatus.OK).body("Bank Account deleted successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body("Sorry, cannot delete bank account with cards in it.");
		}

	}

}
