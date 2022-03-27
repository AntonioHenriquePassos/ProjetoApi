package com.antonio.Api.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.antonio.Api.dtos.BankAccountDto;
import com.antonio.Api.dtos.CardTypeDto;
import com.antonio.Api.dtos.CardsDto;
import com.antonio.Api.models.BankAccount;
import com.antonio.Api.models.CardFlag;
import com.antonio.Api.models.CardName;
import com.antonio.Api.models.CardType;
import com.antonio.Api.models.Cards;
import com.antonio.Api.models.Type;
import com.antonio.Api.repositories.BankAccountRepository;
import com.antonio.Api.repositories.CardTypeRepository;
import com.antonio.Api.repositories.CardsRepository;
import com.antonio.Api.services.exceptions.BankAccountNotFoundException;
import com.antonio.Api.services.exceptions.CardNotFoundException;
import com.antonio.Api.services.exceptions.CardTypeNotFoundException;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceTest {

	@InjectMocks
	private BankAccountService bankAccountService;

	@Mock
	private BankAccountRepository bankAccountRepository;
	@Mock
	private CardsRepository cardsRepository;
	@Mock
	private CardTypeRepository cardTypeRepository;

	BankAccount bankAccount;
	Cards card;
	CardType cardType;
	CardTypeDto dtoCardType;
	CardsDto dtoCard;
	BankAccountDto dtoBankAccount;
	Optional<Cards> cardOptional;
	Optional<BankAccount> bankAccountOptional;
	Optional<CardType> cardTypeOptional;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		startModels();
		bankAccountService = new BankAccountService(cardsRepository, cardTypeRepository, bankAccountRepository);

	}

	private void startModels() {

		dtoCardType = new CardTypeDto(Type.GIFT_CARD);
		dtoCard = new CardsDto(CardName.PRIME, CardFlag.MASTERCARD, "07", "513", BigDecimal.valueOf(100.00),
				Type.GIFT_CARD);
		dtoBankAccount = new BankAccountDto("Maria", "001", "123", "555", "1");
		dtoBankAccount.getCards().add(dtoCard);

		// Models receive the DTOs
		bankAccount = new BankAccount(1L, dtoBankAccount.getNameOwner(), dtoBankAccount.getAgencyCode(),
				dtoBankAccount.getAccountCode(), dtoBankAccount.getRegisterId(),
				dtoBankAccount.getVerificationDigital());

		cardType = new CardType(1, dtoCardType.getTypeOfCard());

		card = new Cards(1L, dtoCard.getCardName(), dtoCard.getCardFlag(), dtoCard.getCardNumber(),
				dtoCard.getCardSecurityCode(), dtoCard.getCardLimit(), cardType, dtoCard.getType(), bankAccount);

		cardType.getCard().add(card);
		bankAccount.getCards().add(card);

		cardOptional = Optional.of(card);
		bankAccountOptional = Optional.of(bankAccount);
		cardTypeOptional = Optional.of(cardType);

	}

	@Test
	void testCreateBankAccount_MustSaveAnInstanceOfBankAccount() {
		when(bankAccountRepository.save(any())).thenReturn(bankAccount);
		BankAccount bankAccountCreated = bankAccountService.createBankAccount(dtoBankAccount);

		assertNotNull(bankAccountCreated);
		assertEquals(BankAccount.class, bankAccountCreated.getClass());

	}

	@Test
	void testCreateCardType_ItMustSaveAnInstanceOfCardType() {
		when(cardTypeRepository.save(any())).thenReturn(cardType);
		CardType cardTypeFound = bankAccountService.createCardType(dtoCardType);
		assertNotNull(cardTypeFound);
		assertEquals(CardType.class, cardTypeFound.getClass());

	}

	@Test
	void testAddCardToBankAccount_ItMustSaveAnInstanceOfBankAccount() {
		when(bankAccountRepository.save(any())).thenReturn(bankAccount);
		BankAccount bankAccountCreated = bankAccountService.createBankAccount(dtoBankAccount);

		assertNotNull(bankAccountCreated);
		assertEquals(BankAccount.class, bankAccountCreated.getClass());

	}

	@Test
	void testFindAll_MustReturnAListOfBankAccount() {
		when(bankAccountRepository.findAll()).thenReturn(List.of(bankAccount));
		List<BankAccount> listFound = bankAccountService.findAll();
		assertNotNull(listFound);
		assertEquals(1, listFound.size());
		assertEquals(BankAccount.class, listFound.get(0).getClass());

	}

	@Test
	void testFindCardById_MustReturnOneCardsInstance() {
		when(cardsRepository.findById(anyLong())).thenReturn(cardOptional);
		Cards cardFound = bankAccountService.findCardById(card.getId());
		assertNotNull(cardFound);
		assertEquals(Cards.class, cardFound.getClass());

	}

	@Test
	void testFindCardById_MustReturnAnObjectCardNotFoundException() {
		when(cardsRepository.findById(anyLong()))
				.thenThrow(new CardNotFoundException("Id not found, id: " + anyLong()));
		try {
			bankAccountService.findCardById(0L);
		} catch (Exception ex) {
			assertEquals(CardNotFoundException.class, ex.getClass());
			assertThat(ex.getMessage()).isEqualTo("Id not found, id: 0");
		}

	}

	@Test
	void testFindBankAccountId_MustReturnOneBankAccoutInstance() {
		when(bankAccountRepository.findById(anyLong())).thenReturn(bankAccountOptional);
		BankAccount bankAccountFound = bankAccountService.findBankAccountId(bankAccount.getId());
		assertNotNull(bankAccountFound);
		assertEquals(BankAccount.class, bankAccountFound.getClass());
	}

	@Test
	void testFindBankAccountId_MustReturnAnObjectBankAccountNotFoundException() {
		when(bankAccountRepository.findById(anyLong()))
				.thenThrow(new BankAccountNotFoundException("Id not found, id: " + anyLong()));
		try {
			bankAccountService.findBankAccountId(0L);
		} catch (Exception ex) {
			assertEquals(BankAccountNotFoundException.class, ex.getClass());
			assertThat(ex.getMessage()).isEqualTo("Id not found, id: 0");
		}

	}

	@Test
	void testFindCardTypeId_MustReturnOneCardTypeInstance() {
		when(cardTypeRepository.findById(anyInt())).thenReturn(cardTypeOptional);
		CardType cardTypeFound = bankAccountService.findCardTypeId(cardType.getId());
		assertNotNull(cardTypeFound);
		assertEquals(CardType.class, cardTypeFound.getClass());
	}

	@Test
	void testFindCardTypeId_MustReturnAnObjectCardTypeNotFoundException() {
		when(cardTypeRepository.findById(anyInt()))
				.thenThrow(new CardTypeNotFoundException("Id not found, id: " + anyInt()));
		try {
			bankAccountService.findCardTypeId(0);
		} catch (Exception ex) {
			assertEquals(CardTypeNotFoundException.class, ex.getClass());
			assertThat(ex.getMessage()).isEqualTo("Id not found, id: 0");
		}
	}

	@Test
	void testUpdateBankAccount__MustSaveAnInstanceOfBankAccount() {
		when(bankAccountRepository.save(any())).thenReturn(bankAccount);
		BankAccount bankAccountUpdated = bankAccountService.updateBankAccount(bankAccount, dtoBankAccount);

		assertNotNull(bankAccountUpdated);
		assertEquals(BankAccount.class, bankAccountUpdated.getClass());

	}

	@Test
	void testUpdateCard_ItMustSaveAnInstanceOfCard() {
		when(cardsRepository.save(any())).thenReturn(card);
		Cards cardUpdated = bankAccountService.updateCard(card, dtoCard);

		assertNotNull(cardUpdated);
		assertEquals(Cards.class, cardUpdated.getClass());
	}

	@Test
	void testDeleteBankAccountId() {
		bankAccountService.deleteBankAccountId(anyLong());
		verify(bankAccountRepository).deleteById(anyLong());

	}

	@Test
	void testDeleteCard() {
		bankAccountService.deleteCardId(anyLong());
		verify(cardsRepository).deleteById(anyLong());
	}

	@Test
	void testDeleteCardTypeId() {
		bankAccountService.deleteCardTypeId(anyInt());
		verify(cardTypeRepository).deleteById(anyInt());
	}

}
