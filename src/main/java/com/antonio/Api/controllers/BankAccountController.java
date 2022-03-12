package com.antonio.Api.controllers;



import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.smartcardio.Card;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antonio.Api.dtos.BankAccountDto;
import com.antonio.Api.dtos.CardTypeDto;
import com.antonio.Api.dtos.CardsDto;
import com.antonio.Api.models.BankAccount;
import com.antonio.Api.models.CardType;
import com.antonio.Api.models.Cards;
import com.antonio.Api.repositories.BankAccountRepository;
import com.antonio.Api.repositories.CardTypeRepository;
import com.antonio.Api.repositories.CardsRepository;
import com.antonio.Api.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class BankAccountController {
	
	public BankAccountService bankAccountService;
	public CardsRepository cardsRepository;
	public CardTypeRepository cardTypeRepository;
	public BankAccountRepository bankAccountRepository;
	
	
	
	public BankAccountController(BankAccountService bankAccountService, CardsRepository cardsRepository,
				CardTypeRepository cardTypeRepository, BankAccountRepository bankAccountRepository) {
			this.bankAccountService = bankAccountService;
			this.cardsRepository = cardsRepository;
			this.cardTypeRepository = cardTypeRepository;
			this.bankAccountRepository = bankAccountRepository;
	}
	
		
	@PostMapping
	public ResponseEntity<Object> createAccount(@RequestBody @Valid BankAccountDto dto){
		BankAccount newBankAccount = new BankAccount();
		newBankAccount = bankAccountService.createBankAccount(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(newBankAccount);
	}
	
	@PostMapping("/cardType/")
	public ResponseEntity<Object> createCardType(@RequestBody CardTypeDto cardTypeDto){
		return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.createCardType(cardTypeDto));

	}
	
	
	@GetMapping("/bankAccountId/{id}")
	public ResponseEntity<Object> findAccountById(@PathVariable(value = "id") Long id){
		Optional<BankAccount> bankAccountOptional = bankAccountService.findBankAccountId(id);
		if (!bankAccountOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bank Account not found.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(bankAccountOptional.get());

	}
	@GetMapping
	public List<BankAccount> listAllAccounts(){
		return bankAccountService.findAll();
	}
	
	@GetMapping("/cardtype/{id}")
	public ResponseEntity<Object> getCardTypeById(@PathVariable(value = "id") Integer id){
		Optional<CardType> cardTypeOptional = bankAccountService.findCardTypeId(id);
		if (!cardTypeOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card type not found.");

		}
		return ResponseEntity.status(HttpStatus.OK).body(cardTypeOptional.get());
	}
	
		
	@DeleteMapping("/cardId/{id}")
	public ResponseEntity<Object> deleteCardId(@PathVariable(value="id")Long id){
		Optional <Cards> cardOptional= bankAccountService.findCardById(id);
		if(cardOptional.isPresent()) {
		Cards card = cardOptional.get();
		bankAccountService.deleteCard(card);
        return ResponseEntity.status(HttpStatus.OK).body("Card deleted successfully.");
	} else {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found");
	}
		
	}
	
	
	@DeleteMapping("/bankAccountId/{id}")
	public ResponseEntity<Object> deleteBankAccountId(@PathVariable(value="id") Long id){
		Optional <BankAccount> bankAccountOptional = bankAccountService.findBankAccountId(id);
		if (bankAccountOptional.isPresent()) {
			if (bankAccountOptional.get().getCards().isEmpty()) {
				bankAccountService.deleteBankAccountId(id);
		        return ResponseEntity.status(HttpStatus.OK).body("Bank Account deleted successfully.");
			} else {			
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cannot delete bank account containing cards.");
			}
	
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bank Account not found");
		}
	}
	
	@PutMapping("/addCard/{id}")
	public ResponseEntity<Object> AddCardToBankAccount(@PathVariable (value="id") Long id, 
		@RequestBody @Valid CardsDto dtoCard){
		Optional <BankAccount> bankAccountOptional = bankAccountService.findBankAccountId(id);
		if (!bankAccountOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bank account not found.");
	} else {
        return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.
        AddCardToBankAccount(dtoCard, id));
	}
	}
	
		

		
		

}
