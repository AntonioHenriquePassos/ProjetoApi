//package com.antonio.Api.controllers;
//
//
//
//import java.util.List;
//
//import javax.validation.Valid;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.antonio.Api.dtos.BankAccountDto;
//import com.antonio.Api.dtos.CardTypeDto;
//import com.antonio.Api.dtos.CardsDto;
//import com.antonio.Api.models.BankAccount;
//import com.antonio.Api.models.CardType;
//import com.antonio.Api.models.Cards;
//import com.antonio.Api.repositories.BankAccountRepository;
//import com.antonio.Api.repositories.CardTypeRepository;
//import com.antonio.Api.repositories.CardsRepository;
//import com.antonio.Api.services.BankAccountService;
//
//import io.swagger.annotations.*;
//import io.swagger.annotations.ApiOperation;
//
//@RestController
//@RequestMapping("/bank")
//@Api(value="ProjetoApi")
//@CrossOrigin(origins="*")
//public class BankAccountController {
//	
//	public BankAccountService bankAccountService;
//	public CardsRepository cardsRepository;
//	public CardTypeRepository cardTypeRepository;
//	public BankAccountRepository bankAccountRepository;
//	
//	
//	
//	public BankAccountController(BankAccountService bankAccountService, CardsRepository cardsRepository,
//				CardTypeRepository cardTypeRepository, BankAccountRepository bankAccountRepository) {
//			this.bankAccountService = bankAccountService;
//			this.cardsRepository = cardsRepository;
//			this.cardTypeRepository = cardTypeRepository;
//			this.bankAccountRepository = bankAccountRepository;
//	}
//	
//		
//	@PostMapping
//	@ApiOperation(value="Create a bank account with cards or not.")
//	public ResponseEntity<Object> createBankAccount(@RequestBody @Valid BankAccountDto dto){
//		BankAccount newBankAccount = new BankAccount();
//		newBankAccount = bankAccountService.createBankAccount(dto);
//		return ResponseEntity.status(HttpStatus.CREATED).body(newBankAccount);
//	}
//	
//	@PostMapping("/cardType/")
//	@ApiOperation(value="Create a card type.")
//	public ResponseEntity<Object> createCardType(@RequestBody CardTypeDto cardTypeDto){
//		return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.createCardType(cardTypeDto));
//
//	}
//	
//	@PostMapping("/card/{id}")
//	@ApiOperation(value="Create a card which must be added to a bank account. "
//			+ " The \"id\" references the Bank AccountÂ´s id to which the card will be added.")
//	public ResponseEntity<Object> createCard(@PathVariable (value="id") Long id, 
//		@RequestBody @Valid CardsDto dtoCard){
//		BankAccount bankAccountFound = bankAccountService.findBankAccountId(id);
//        return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.AddCardToBankAccount(dtoCard, bankAccountFound));
//	}
//	
//	@GetMapping
//	@ApiOperation(value="List all bank accounts with theis respective cards.")
//	public List<BankAccount> listAllBankAccountsWithCards(){
//		return bankAccountService.findAll();
//	}
//	
//	@GetMapping("/bankAccount/{id}")
//	@ApiOperation(value="Find a bank acount by its id.")
//	public ResponseEntity<Object> findAccountById(@PathVariable(value = "id") Long id){
//		BankAccount bankAccount= bankAccountService.findBankAccountId(id);
//		return ResponseEntity.status(HttpStatus.OK).body(bankAccount);
//
//	}
//	
//	
//	@GetMapping("/cardtype/{id}")
//	@ApiOperation(value="Find a card by its id.")
//	public ResponseEntity<Object> findCardTypeById(@PathVariable(value = "id") Integer id){
//		CardType cardType= bankAccountService.findCardTypeId(id);
//		return ResponseEntity.status(HttpStatus.OK).body(cardType);
//	}
//	
//	
//	@PutMapping("/updateBankAccount/{id}")
//	@ApiOperation(value="Update only the bank account information,"
//			+ " cards will be updated with their proper function.")
//	public ResponseEntity<Object> updateBankAccount(@PathVariable(value="id")Long id,
//	@RequestBody @Valid BankAccountDto bankAccountDto){
//		BankAccount BankAccountFound = bankAccountService.findBankAccountId(id);
//		return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.
//				updateBankAccount(BankAccountFound, bankAccountDto));
//		
//	}
//	
//	
//	@PutMapping("/updateCard/{id}")
//	@ApiOperation(value="Update an existing card, found by its id.")
//	public ResponseEntity<Object> updateCard(@PathVariable(value="id") Long id, 
//		@RequestBody @Valid CardsDto cardDto ){
//		Cards cardFound = bankAccountService.findCardById(id);
//		return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.
//				updateCard(cardFound, cardDto));
//	}
////	//Must be used only in case of creation of ENUM "Type" which will replace an existing one.
////	@PutMapping("/updateCardType/{id}")
////	public ResponseEntity<Object> updateCardType(@PathVariable(value="id") Integer id, 
////		@RequestBody @Valid CardTypeDto cardTypeDto ){
////		CardType cardTypeFound = bankAccountService.findCardTypeId(id);
////		return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.
////				updateCardType(cardTypeFound, cardTypeDto));
////	}
//		
//	@DeleteMapping("/deleteCard/{id}")
//	@ApiOperation(value="Delete a card, found by its id.")
//	public ResponseEntity<Object> deleteCardId(@PathVariable(value="id")Long id){
//		Cards card = bankAccountService.findCardById(id);
//		return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.deleteCard(card));
//	}
//
//	
//	
//	@DeleteMapping("/deleteBankAccount/{id}")
//	@ApiOperation(value="Delete a bank account, found by its id."
//			+ " The deletion accurs only if there are no cards related to the bank account.")
//	public ResponseEntity<Object> deleteBankAccountId(@PathVariable(value="id") Long id){
//		BankAccount bankAccountFound = bankAccountService.findBankAccountId(id);
//		if (bankAccountFound.getCards().isEmpty()) {
//			bankAccountService.deleteBankAccountId(id);
//	        return ResponseEntity.status(HttpStatus.OK).body("Bank Account deleted successfully.");
//		} else {			
//			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, cannot delete bank account with cards in it.");
//		}
//		
//	} 
//	
//	@DeleteMapping("/deleteCardType/{id}")
//	@ApiOperation(value="Delete a card type, found by its id."
//			+ " The deletion accurs only if there are no cards related to the card type.")	
//	public ResponseEntity<Object> deleteCardTypeId(@PathVariable(value="id") Integer id){
//		CardType cardTypeFound = bankAccountService.findCardTypeId(id);
//		if (cardTypeFound.getCard().isEmpty()) {
//			bankAccountService.deleteCardTypeId(id);
//	        return ResponseEntity.status(HttpStatus.OK).body("Card type deleted successfully.");
//		} else {			
//			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sorry, cannot delete card type with cards in it.");
//		}
//		
//	}
//
//
//
//}
