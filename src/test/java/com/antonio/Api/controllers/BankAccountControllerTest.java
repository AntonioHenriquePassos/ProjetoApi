package com.antonio.Api.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.antonio.Api.dtos.BankAccountDto;
import com.antonio.Api.dtos.CardTypeDto;
import com.antonio.Api.dtos.CardsDto;
import com.antonio.Api.models.BankAccount;
import com.antonio.Api.models.CardFlag;
import com.antonio.Api.models.CardName;
import com.antonio.Api.models.CardType;
import com.antonio.Api.models.Cards;
import com.antonio.Api.models.Type;
import com.antonio.Api.services.BankAccountService;
import com.antonio.Api.services.exceptions.BankAccountNotFoundException;

@SpringBootTest
class BankAccountControllerTest {

	@InjectMocks
	private BankAccountController bankAccountController;

	@Mock
	private BankAccountService bankAccountService;

	BankAccount bankAccount;

	Cards card;
	CardType cardType;
	CardTypeDto dtoCardType;
	CardsDto dtoCard;
	BankAccountDto dtoBankAccount;
	Optional<Cards> cardOptional;
	Optional<BankAccount> bankAccountOptional;
	Optional<CardType> cardTypeOptional;

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

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startModels();
		bankAccountController = new BankAccountController(bankAccountService);

	}

	@Test
	void testCreateBankAccount_MustReturnOneInstanceOfBankAccount() {
		when(bankAccountService.createBankAccount(any())).thenReturn(bankAccount);
		ResponseEntity<BankAccount> newBankAccountResponse = bankAccountController.createBankAccount(dtoBankAccount);

		assertNotNull(newBankAccountResponse);
		assertNotNull(newBankAccountResponse.getHeaders().get("Location"));
		assertEquals(ResponseEntity.class, newBankAccountResponse.getClass());
		assertEquals(HttpStatus.CREATED, newBankAccountResponse.getStatusCode());

	}

	@Test
	void testCreateBankAccount_MustReturnDataIntegrityViolationException() {
		when(bankAccountService.createBankAccount(any())).thenThrow(new DataIntegrityViolationException("message"));

		try {
			bankAccountController.createBankAccount(dtoBankAccount);
		} catch (Exception ex) {
			assertEquals(DataIntegrityViolationException.class, ex.getClass());
		}

	}

	@Test
	void testListAllBankAccountsWithCards_MustReturnAListOfBankAccount() {
		when(bankAccountService.findAll()).thenReturn(List.of(bankAccount));
		ResponseEntity<List<BankAccount>> listResponse = bankAccountController.listAllBankAccountsWithCards();

		assertNotNull(listResponse);
		assertNotNull(listResponse.getBody());
		assertEquals(ResponseEntity.class, listResponse.getClass());
		assertEquals(HttpStatus.OK, listResponse.getStatusCode());
		assertEquals(BankAccount.class, listResponse.getBody().get(0).getClass());
		assertEquals(bankAccount.getId(), (listResponse.getBody()).get(0).getId());

	}

	@Test
	void testFindAccountById_MustReturnOneBankAccountInstance() {
		when(bankAccountService.findBankAccountId(anyLong())).thenReturn(bankAccount);

		ResponseEntity<BankAccount> bankAccountResponse = bankAccountController.findAccountById(anyLong());

		assertNotNull(bankAccountResponse);
		assertNotNull(bankAccountResponse.getBody());
		assertEquals(ResponseEntity.class, bankAccountResponse.getClass());
		assertEquals(BankAccount.class, bankAccountResponse.getBody().getClass());
		assertEquals(HttpStatus.OK, bankAccountResponse.getStatusCode());
		assertEquals(bankAccount.getId(), (bankAccountResponse.getBody()).getId());

	}

	@Test
	void testFindAccountById_MustReturnAnObjectBankAccountNotFoundException() {
		when(bankAccountService.findBankAccountId(anyLong()))
				.thenThrow(new BankAccountNotFoundException("Id not found, id: " + anyLong()));

		try {
			bankAccountController.findAccountById(0L);
		} catch (Exception ex) {
			assertEquals(BankAccountNotFoundException.class, ex.getClass());
			assertThat(ex.getMessage()).isEqualTo("Id not found, id: 0");
		}

	}

	@Test
	void testUpdateBankAccount_MustReturnOneBankAccountInstance() {
		when(bankAccountService.findBankAccountId(anyLong())).thenReturn(bankAccount);
		when(bankAccountService.updateBankAccount(any(), any())).thenReturn(bankAccount);
		ResponseEntity<BankAccount> bankAccountResponse = bankAccountController.updateBankAccount(anyLong(),
				dtoBankAccount);

		assertNotNull(bankAccountResponse);
		assertNotNull(bankAccountResponse.getBody());
		assertEquals(ResponseEntity.class, bankAccountResponse.getClass());
		assertEquals(BankAccount.class, bankAccountResponse.getBody().getClass());
		assertEquals(HttpStatus.OK, bankAccountResponse.getStatusCode());
		assertEquals(bankAccount.getId(), (bankAccountResponse.getBody()).getId());

	}

	@Test
	void testUpdateBankAccount_MustReturnAnObjectBankAccountNotFoundException() {
		when(bankAccountService.findBankAccountId(anyLong()))
				.thenThrow(new BankAccountNotFoundException("Id not found, id: " + anyLong()));

		try {
			bankAccountController.updateBankAccount(0L, dtoBankAccount);
		} catch (Exception ex) {
			assertEquals(BankAccountNotFoundException.class, ex.getClass());
			assertThat(ex.getMessage()).isEqualTo("Id not found, id: 0");
		}

	}

	@Test
	void testUpdateBankAccount_MustReturnDataIntegrityViolationException() {
		when(bankAccountService.findBankAccountId(anyLong())).thenReturn(bankAccount);
		when(bankAccountService.updateBankAccount(any(), any()))
				.thenThrow(new DataIntegrityViolationException("message"));

		try {
			bankAccountController.updateBankAccount(anyLong(), dtoBankAccount);
		} catch (Exception ex) {
			assertEquals(DataIntegrityViolationException.class, ex.getClass());
		}

	}

	@Test
	void testDeleteBankAccount_MustReturnStatusOK() {
		bankAccount.getCards().remove(0);
		when(bankAccountService.findBankAccountId(anyLong())).thenReturn(bankAccount);
		doNothing().when(bankAccountService).deleteBankAccountId(anyLong());

		ResponseEntity<Object> bankAccountResponse = bankAccountController.deleteBankAccount(anyLong());

		assertNotNull(bankAccountResponse);
		assertEquals(ResponseEntity.class, bankAccountResponse.getClass());
		assertEquals(HttpStatus.OK, bankAccountResponse.getStatusCode());
		verify(bankAccountService, times(1)).deleteBankAccountId(anyLong());

	}

	@Test
	void testDeleteBankAccount_MustReturnStatusFORBIDDEN() {
		bankAccount.getCards().add(card);
		when(bankAccountService.findBankAccountId(anyLong())).thenReturn(bankAccount);
		doNothing().when(bankAccountService).deleteBankAccountId(anyLong());

		ResponseEntity<Object> bankAccountResponse = bankAccountController.deleteBankAccount(anyLong());

		assertNotNull(bankAccountResponse);
		assertEquals(ResponseEntity.class, bankAccountResponse.getClass());
		assertEquals(HttpStatus.FORBIDDEN, bankAccountResponse.getStatusCode());

	}

	@Test
	void testDeleteBankAccount_MustReturnBankAccountNotFoundException() {
		when(bankAccountService.findBankAccountId(anyLong()))
				.thenThrow(new BankAccountNotFoundException("Id not found, id: " + anyLong()));

		try {
			bankAccountController.deleteBankAccount(0L);
		} catch (Exception ex) {
			assertEquals(BankAccountNotFoundException.class, ex.getClass());
			assertThat(ex.getMessage()).isEqualTo("Id not found, id: 0");
		}

	}

}
