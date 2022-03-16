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
	
	public BankAccountService (CardsRepository cardsRepository, 
	CardTypeRepository cardTypeRepository, BankAccountRepository bankAccountRepository ) {
		
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
			Optional<CardType> cardTypeOptional = cardTypeRepository.findById(cardDto.getidtype());
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

 
	@Transactional
	public BankAccount AddCardToBankAccount(CardsDto dtoCard, BankAccount foundBankAccount) {
		BankAccount updatedBankAccount = new BankAccount();
		updatedBankAccount = foundBankAccount;
		Cards newCard = new Cards();
		newCard.setCardFlag(dtoCard.getCardFlag());
		newCard.setCardLimit(dtoCard.getCardLimit());
		newCard.setCardName(dtoCard.getCardName());
		newCard.setCardNumber(dtoCard.getCardNumber());
		newCard.setCardSecurityCode(dtoCard.getCardSecurityCode());
		newCard.setBankAccount(updatedBankAccount);
		Optional<CardType> cardTypeOptional = cardTypeRepository.findById(dtoCard.getidtype());
		CardType cardType = new CardType();
		cardType = cardTypeOptional.get();
		cardType.getCard().add(newCard);
		newCard.setCardType(cardType);
		newCard.setType(cardType.getTypeOfCard());
		cardsRepository.save(newCard);
		cardTypeRepository.save(cardType);
		updatedBankAccount.getCards().add(newCard);
		updatedBankAccount.setId(foundBankAccount.getId());
		bankAccountRepository.save(updatedBankAccount);
		return bankAccountRepository.findById(updatedBankAccount.getId()).get();
				
		
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
	
	
	public BankAccount updateBankAccount (BankAccount foundBankAccount, BankAccountDto dto) {
		BankAccount updatedBankAccount = new BankAccount();
		updatedBankAccount.setAccountCode(dto.getAccountCode());
		updatedBankAccount.setAgencyCode(dto.getAgencyCode());
		updatedBankAccount.setNameOwner(dto.getNameOwner());
		updatedBankAccount.setRegisterId(dto.getRegisterId());
		updatedBankAccount.setVerificationDigital(dto.getVerificationDigital());
		updatedBankAccount.setId(foundBankAccount.getId());
		updatedBankAccount.setCards(foundBankAccount.getCards());
		return bankAccountRepository.save(updatedBankAccount);			
	}
	
	public Cards updateCard(Cards cardFound, CardsDto cardDto) {
		Cards newCard = new Cards();
		newCard.setCardFlag(cardDto.getCardFlag());
		newCard.setCardLimit(cardDto.getCardLimit());
		newCard.setCardName(cardDto.getCardName());
		newCard.setCardNumber(cardDto.getCardNumber());
		newCard.setCardSecurityCode(cardDto.getCardSecurityCode());
		newCard.setBankAccount(cardFound.getBankAccount());
		newCard.setId(cardFound.getId());
		
		if(cardDto.getidtype()== cardFound.getCardType().getId()) {
			newCard.setCardType(cardFound.getCardType());
			newCard.setType(cardFound.getCardType().getTypeOfCard());
			cardsRepository.save(newCard);
			return cardsRepository.findById(newCard.getId()).get();

		} 
		
		CardType cardTypeFound = cardTypeRepository.findById(cardDto.getidtype()).get();
		newCard.setCardType(cardTypeFound);
		newCard.setType(cardTypeFound.getTypeOfCard());
		cardsRepository.save(newCard);
		cardTypeFound.getCard().add(newCard);
		cardTypeRepository.save(cardTypeFound);
		return cardsRepository.findById(newCard.getId()).get();
	}
	
	
//	public CardType updateCardType(CardType foundCardType, CardTypeDto dtoCardType ) {
//		CardType updatedCardType = new CardType();
//		updatedCardType.setId(foundCardType.getId());
//		updatedCardType.setCard(foundCardType.getCard());
//		updatedCardType.setTypeOfCard(dtoCardType.getTypeOfCard());
//		cardTypeRepository.save(updatedCardType);
//		return cardTypeRepository.findById(updatedCardType.getId()).get();
//	}

	
	@Transactional
	public void deleteBankAccountId(Long id) {
		bankAccountRepository.deleteById(id);
		
	}
	
	@Transactional
	public String deleteCard(Cards card) {
		cardsRepository.delete(card);
		return "Card deleted successfully.";
	}
	
	@Transactional
	public void deleteCardTypeId(Integer id) {
		cardTypeRepository.deleteById(id);
		
	}
	
	
}
