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
		BankAccount bankAccount= bankAccountService.findBankAccountId(id);
		return ResponseEntity.status(HttpStatus.OK).body(bankAccount);

	}
	@GetMapping
	public List<BankAccount> listAllAccounts(){
		return bankAccountService.findAll();
	}
	
	@GetMapping("/cardtype/{id}")
	public ResponseEntity<Object> getCardTypeById(@PathVariable(value = "id") Integer id){
		CardType cardType= bankAccountService.findCardTypeId(id);
		return ResponseEntity.status(HttpStatus.OK).body(cardType);
	}
	
		
	@DeleteMapping("/cardId/{id}")
	public ResponseEntity<Object> deleteCardId(@PathVariable(value="id")Long id){
		Cards card = bankAccountService.findCardById(id);
		return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.deleteCard(card));
	}

	
	
	@DeleteMapping("/bankAccountId/{id}")
	public ResponseEntity<Object> deleteBankAccountId(@PathVariable(value="id") Long id){
		BankAccount bankAccount = bankAccountService.findBankAccountId(id);
		if (bankAccount.getCards().isEmpty()) {
			bankAccountService.deleteBankAccountId(id);
	        return ResponseEntity.status(HttpStatus.OK).body("Bank Account deleted successfully.");
		} else {			
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cannot delete bank account containing cards.");
		}

	} 
	
	
	@PutMapping("/addCard/{id}")
	public ResponseEntity<Object> AddCardToBankAccount(@PathVariable (value="id") Long id, 
		@RequestBody @Valid CardsDto dtoCard){
		BankAccount bankAccount = bankAccountService.findBankAccountId(id);
        return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.AddCardToBankAccount(dtoCard, id));
	}
	
	
		

		
		

}
