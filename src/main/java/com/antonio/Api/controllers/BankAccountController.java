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
	public ResponseEntity<Object> createBankAccount(@RequestBody @Valid BankAccountDto dto){
		BankAccount newBankAccount = new BankAccount();
		newBankAccount = bankAccountService.createBankAccount(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(newBankAccount);
	}
	
	@PostMapping("/cardType/")
	public ResponseEntity<Object> createCardType(@RequestBody CardTypeDto cardTypeDto){
		return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.createCardType(cardTypeDto));

	}
	//The below "id" references the Bank Account to which a new card will be added.
	@PostMapping("/card/{id}")
	public ResponseEntity<Object> createCard(@PathVariable (value="id") Long id, 
		@RequestBody @Valid CardsDto dtoCard){
		BankAccount bankAccountFound = bankAccountService.findBankAccountId(id);
        return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.AddCardToBankAccount(dtoCard, bankAccountFound));
	}
	
	@GetMapping
	public List<BankAccount> listAllBankAccountsWithCards(){
		return bankAccountService.findAll();
	}
	
	@GetMapping("/bankAccount/{id}")
	public ResponseEntity<Object> findAccountById(@PathVariable(value = "id") Long id){
		BankAccount bankAccount= bankAccountService.findBankAccountId(id);
		return ResponseEntity.status(HttpStatus.OK).body(bankAccount);

	}
	
	
	@GetMapping("/cardtype/{id}")
	public ResponseEntity<Object> findCardTypeById(@PathVariable(value = "id") Integer id){
		CardType cardType= bankAccountService.findCardTypeId(id);
		return ResponseEntity.status(HttpStatus.OK).body(cardType);
	}
	
	
	//Update only bank account data, cards are updated in their proper function.
	@PutMapping("/updateBankAccount/{id}")
	public ResponseEntity<Object> updateBankAccount(@PathVariable(value="id")Long id,
	@RequestBody @Valid BankAccountDto bankAccountDto){
		BankAccount BankAccountFound = bankAccountService.findBankAccountId(id);
		return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.
				updateBankAccount(BankAccountFound, bankAccountDto));
		
	}
	
	
	@PutMapping("/updateCard/{id}")
	public ResponseEntity<Object> updateCard(@PathVariable(value="id") Long id, 
		@RequestBody @Valid CardsDto cardDto ){
		Cards cardFound = bankAccountService.findCardById(id);
		return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.
				updateCard(cardFound, cardDto));
	}
//	//Must be used only in case of creation of ENUM "Type" which will replace an existing one.
//	@PutMapping("/updateCardType/{id}")
//	public ResponseEntity<Object> updateCardType(@PathVariable(value="id") Integer id, 
//		@RequestBody @Valid CardTypeDto cardTypeDto ){
//		CardType cardTypeFound = bankAccountService.findCardTypeId(id);
//		return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.
//				updateCardType(cardTypeFound, cardTypeDto));
//	}
		
	@DeleteMapping("/deleteCard/{id}")
	public ResponseEntity<Object> deleteCardId(@PathVariable(value="id")Long id){
		Cards card = bankAccountService.findCardById(id);
		return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.deleteCard(card));
	}

	
	
	@DeleteMapping("/deleteBankAccount/{id}")
	public ResponseEntity<Object> deleteBankAccountId(@PathVariable(value="id") Long id){
		BankAccount bankAccountFound = bankAccountService.findBankAccountId(id);
		if (bankAccountFound.getCards().isEmpty()) {
			bankAccountService.deleteBankAccountId(id);
	        return ResponseEntity.status(HttpStatus.OK).body("Bank Account deleted successfully.");
		} else {			
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, cannot delete bank account with cards in it.");
		}
		
	} 
	
	@DeleteMapping("/deleteCardType/{id}")
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
