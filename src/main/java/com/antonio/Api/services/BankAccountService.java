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

	public BankAccountService(CardsRepository cardsRepository, CardTypeRepository cardTypeRepository,
			BankAccountRepository bankAccountRepository) {

		this.cardsRepository = cardsRepository;
		this.cardTypeRepository = cardTypeRepository;
		this.bankAccountRepository = bankAccountRepository;
	}

	@Transactional
	public BankAccount createBankAccount(BankAccountDto dto) {
		BankAccount newBankAccount = new BankAccount();
		newBankAccount.setAccountCode(dto.getAccountCode());
		newBankAccount.setAgencyCode(dto.getAgencyCode());
		newBankAccount.setNameOwner(dto.getNameOwner());
		newBankAccount.setRegisterId(dto.getRegisterId());
		newBankAccount.setVerificationDigital(dto.getVerificationDigital());

		for (CardsDto cardDto : dto.getCards()) {
			Cards newCard = new Cards();
			newCard.setCardFlag(cardDto.getCardFlag());
			newCard.setCardLimit(cardDto.getCardLimit());
			newCard.setCardName(cardDto.getCardName());
			newCard.setCardNumber(cardDto.getCardNumber());
			newCard.setCardSecurityCode(cardDto.getCardSecurityCode());
			newCard.setBankAccount(newBankAccount);
			Optional<CardType> cardTypeOptional = cardTypeRepository.findByTypeOfCard(cardDto.getType());
			if (cardTypeOptional.isEmpty()) {
				CardType cardType = new CardType();
				cardType.setTypeOfCard(cardDto.getType());
				cardType.getCard().add(newCard);
				newCard.setCardType(cardType);
				newCard.setType(cardType.getTypeOfCard());
				cardsRepository.save(newCard);
				cardTypeRepository.save(cardType);
				newBankAccount.getCards().add(newCard);
				bankAccountRepository.save(newBankAccount);

			} else {

				CardType cardType = new CardType();
				cardType = cardTypeOptional.get();
				cardType.getCard().add(newCard);
				newCard.setCardType(cardType);
				newCard.setType(cardType.getTypeOfCard());
				cardsRepository.save(newCard);
				cardTypeRepository.save(cardType);
				newBankAccount.getCards().add(newCard);
				bankAccountRepository.save(newBankAccount);

			}
		}
		bankAccountRepository.save(newBankAccount);
		return newBankAccount;

	}

	@Transactional
	public CardType createCardType(CardTypeDto cardTypeDto) {
		CardType cardType = new CardType();
		cardType.setTypeOfCard(cardTypeDto.getTypeOfCard());
		return cardTypeRepository.save(cardType);
	}

	@Transactional
	public Cards AddCardToBankAccount(CardsDto cardDto, BankAccount foundBankAccount) {
		BankAccount updatedBankAccount = new BankAccount();
		updatedBankAccount = foundBankAccount;
		Cards newCard = new Cards();
		newCard.setCardFlag(cardDto.getCardFlag());
		newCard.setCardLimit(cardDto.getCardLimit());
		newCard.setCardName(cardDto.getCardName());
		newCard.setCardNumber(cardDto.getCardNumber());
		newCard.setCardSecurityCode(cardDto.getCardSecurityCode());
		newCard.setBankAccount(updatedBankAccount);
		Optional<CardType> cardTypeOptional = cardTypeRepository.findByTypeOfCard(cardDto.getType());
		if (cardTypeOptional.isEmpty()) {
			CardType cardType = new CardType();
			cardType.setTypeOfCard(cardDto.getType());
			cardType.getCard().add(newCard);
			newCard.setCardType(cardType);
			newCard.setType(cardType.getTypeOfCard());
			cardsRepository.save(newCard);
			cardTypeRepository.save(cardType);
			updatedBankAccount.getCards().add(newCard);
			bankAccountRepository.save(updatedBankAccount);

		} else {

			CardType cardType = new CardType();
			cardType = cardTypeOptional.get();
			cardType.getCard().add(newCard);
			newCard.setCardType(cardType);
			newCard.setType(cardType.getTypeOfCard());
			cardsRepository.save(newCard);
			cardTypeRepository.save(cardType);
			updatedBankAccount.getCards().add(newCard);
			bankAccountRepository.save(updatedBankAccount);
		}
		return newCard;
	}

	public List<BankAccount> findAll() {
		return bankAccountRepository.findAll();
	}

	public Cards findCardById(Long id) {
		Optional<Cards> cardFound = cardsRepository.findById(id);
		return cardFound.orElseThrow(() -> new CardNotFoundException("Id not found, id: " + id));
	}

	public BankAccount findBankAccountId(Long id) {
		Optional<BankAccount> bankAccountFound = bankAccountRepository.findById(id);
		return bankAccountFound.orElseThrow(() -> new BankAccountNotFoundException("Id not found, id: " + id));
	}

	public CardType findCardTypeId(Integer id) {
		Optional<CardType> cardTypeFound = cardTypeRepository.findById(id);
		return cardTypeFound.orElseThrow(() -> new CardTypeNotFoundException("Id not found, id: " + id));

	}

	public BankAccount updateBankAccount(BankAccount foundBankAccount, BankAccountDto dto) {
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

		if (cardDto.getType() == cardFound.getType()) {
			newCard.setCardType(cardFound.getCardType());
			newCard.setType(cardFound.getCardType().getTypeOfCard());
			cardsRepository.save(newCard);
			return newCard;

		} else {

			CardType cardTypeFound = cardTypeRepository.findByTypeOfCard(cardDto.getType()).get();
			newCard.setCardType(cardTypeFound);
			newCard.setType(cardTypeFound.getTypeOfCard());
			cardsRepository.save(newCard);
			cardTypeFound.getCard().add(newCard);
			cardTypeRepository.save(cardTypeFound);
			return newCard;
		}
	}

	@Transactional
	public void deleteBankAccountId(Long id) {
		bankAccountRepository.deleteById(id);

	}

	@Transactional
	public String deleteCardId(Long id) {
		cardsRepository.deleteById(id);
		return "Card deleted successfully.";
	}

	@Transactional
	public void deleteCardTypeId(Integer id) {
		cardTypeRepository.deleteById(id);

	}

}
