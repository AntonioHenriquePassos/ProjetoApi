package com.antonio.Api.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.antonio.Api.dtos.BankAccountDto;
import com.antonio.Api.dtos.CardTypeDto;
import com.antonio.Api.dtos.CardsDto;
import com.antonio.Api.models.BankAccount;
import com.antonio.Api.models.CardType;
import com.antonio.Api.models.Cards;
import com.antonio.Api.repositories.BankAccountRepository;
import com.antonio.Api.repositories.CardTypeRepository;
import com.antonio.Api.repositories.CardsRepository;
import com.antonio.Api.services.exceptions.BankAccountNotFoundException;
import com.antonio.Api.services.exceptions.CardNotFoundException;
import com.antonio.Api.services.exceptions.CardTypeNotFoundException;

@Service
public class BankAccountService {
	
	final CardsRepository cardsRepository;
	final CardTypeRepository cardTypeRepository;
	final BankAccountRepository bankAccountRepository;
	
	public BankAccountService (CardsRepository cardsRepository, CardTypeRepository cardTypeRepository, 
	BankAccountRepository bankAccountRepository ) {
		
		this.cardsRepository=cardsRepository;
		this.cardTypeRepository=cardTypeRepository;
		this.bankAccountRepository=bankAccountRepository;
	}
	
	@Transactional
	public BankAccount createBankAccount (BankAccountDto dto) {
		BankAccount newBankAccount = new BankAccount();
		newBankAccount.setAccountCode(dto.getAccountCode());
		newBankAccount.setAgencyCode(dto.getAgencyCode());
		newBankAccount.setNameOwner(dto.getNameOwner());
		newBankAccount.setRegisterId(dto.getRegisterId());
		newBankAccount.setVerificationDigital(dto.getVerificationDigital());
		
		for(CardsDto cardDto:dto.getCards()) {
			Cards newCard = new Cards();
			newCard.setCardFlag(cardDto.getCardFlag());
			newCard.setCardLimit(cardDto.getCardLimit());
			newCard.setCardName(cardDto.getCardName());
			newCard.setCardNumber(cardDto.getCardNumber());
			newCard.setCardSecurityCode(cardDto.getCardSecurityCode());
			newCard.setBankAccount(newBankAccount);
			Optional<CardType> cardTypeOptional = cardTypeRepository.findById(cardDto.getidetype());
			CardType cardType = new CardType();
			cardType = cardTypeOptional.get();
			cardType.getCard().add(newCard);
			newCard.setCardType(cardType);
			newCard.setType(cardType.getTypeOfCard());
			cardsRepository.save(newCard);
			cardTypeRepository.save(cardType);
			newBankAccount.getCards().add(newCard);
		}
		return bankAccountRepository.save(newBankAccount);
		
	}	 
	
	
	
	@Transactional
	public CardType createCardType(CardTypeDto cardTypeDto) {
		CardType cardType = new CardType();
		cardType.setTypeOfCard(cardTypeDto.getTypeOfCard());
		return cardTypeRepository.save(cardType);
	}
	
		
	public List<BankAccount> findAll(){
		return bankAccountRepository.findAll();
	}
	
	public Cards findCardById(Long id){
		return cardsRepository.findById(id).orElseThrow(
				()-> new CardNotFoundException("Id not found, id: " + id));
	}

	public BankAccount findBankAccountId(Long id) {
		return bankAccountRepository.findById(id).orElseThrow(
				() -> new BankAccountNotFoundException("Id not found, id: " + id));
	}
	
	public CardType findCardTypeId(Integer id) {
		return cardTypeRepository.findById(id).orElseThrow(
				()-> new CardTypeNotFoundException("Id not found, id: " + id));
				
	}
	
	
	
	@Transactional
	public String deleteCard(Cards card) {
		cardsRepository.delete(card);
		return "Card deleted successfully.";
	}
	
	
	@Transactional
	public void deleteBankAccountId(Long id) {
		bankAccountRepository.deleteById(id);
	}
	
	 
	@Transactional
	public BankAccount AddCardToBankAccount(CardsDto dtoCard, Long id ) {
		BankAccount bankAccount = bankAccountRepository.findById(id).get();
		Cards newCard = new Cards();
		newCard.setCardFlag(dtoCard.getCardFlag());
		newCard.setCardLimit(dtoCard.getCardLimit());
		newCard.setCardName(dtoCard.getCardName());
		newCard.setCardNumber(dtoCard.getCardNumber());
		newCard.setCardSecurityCode(dtoCard.getCardSecurityCode());
		newCard.setBankAccount(bankAccount);
		Optional<CardType> cardTypeOptional = cardTypeRepository.findById(dtoCard.getidetype());
		CardType cardType = new CardType();
		cardType = cardTypeOptional.get();
		cardType.getCard().add(newCard);
		newCard.setCardType(cardType);
		newCard.setType(cardType.getTypeOfCard());
		cardsRepository.save(newCard);
		cardTypeRepository.save(cardType);
		bankAccount.getCards().add(newCard);
		bankAccount.setId(id);
		bankAccountRepository.save(bankAccount);
				
		return bankAccount;
	}

	
}
