package com.antonio.Api.controllers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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
import com.antonio.Api.services.exceptions.CardNotFoundException;

@SpringBootTest
class CardsControllerTest {

	@InjectMocks
	private CardsController cardsController;
	
	@Mock
	private BankAccountService bankAccountService;
	
	BankAccount bankAccount;

	Cards card;
	CardType cardType;
	CardTypeDto dtoCardType;
	CardsDto dtoCard;
	BankAccountDto dtoBankAccount;
	Optional <Cards> cardOptional;
	Optional <BankAccount> bankAccountOptional;
	Optional <CardType> cardTypeOptional;
	
	private void startModels() {
		
		dtoCardType= new CardTypeDto(Type.GIFT_CARD);
		dtoCard = new CardsDto(CardName.PRIME,CardFlag.MASTERCARD,"07","513", 
		BigDecimal.valueOf(100.00), Type.GIFT_CARD);
		dtoBankAccount = new BankAccountDto("Maria","001","123","555",
		"1");
		dtoBankAccount.getCards().add(dtoCard);
		
		
		//Models receive the DTOs
		bankAccount = new BankAccount(1L,dtoBankAccount.getNameOwner(), 
		dtoBankAccount.getAgencyCode(), dtoBankAccount.getAccountCode(),
		dtoBankAccount.getRegisterId(),dtoBankAccount.getVerificationDigital());
		
		cardType = new CardType(1,dtoCardType.getTypeOfCard());

		card = new Cards(1L, dtoCard.getCardName(), dtoCard.getCardFlag(),
		dtoCard.getCardNumber(),dtoCard.getCardSecurityCode(), dtoCard.getCardLimit(), 
		cardType, dtoCard.getType(),  bankAccount);
		
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
		cardsController = new CardsController(bankAccountService);

	}

	@Test
	void testCreateCard_MustReturnOneCardsInstance() {
		when(bankAccountService.AddCardToBankAccount(any(), any())).thenReturn(card);
		ResponseEntity<Cards> newCardResponse= cardsController.createCard(anyLong(), dtoCard);
		
		assertNotNull(newCardResponse);
		assertNotNull(newCardResponse.getHeaders().get("Location"));
		assertEquals(ResponseEntity.class, newCardResponse.getClass());
		assertEquals(HttpStatus.CREATED, newCardResponse.getStatusCode());
	}
	
	@Test
	void testCreateCard_MustReturnDataIntegrityViolationException() {
		when(bankAccountService.AddCardToBankAccount(any(), any()))
		.thenThrow(new DataIntegrityViolationException("message"));
		
		try {
			cardsController.createCard(anyLong(), dtoCard);
		} catch(Exception ex) {
			assertEquals( DataIntegrityViolationException.class, ex.getClass());
		}
		
	
	}

	@Test
	void testFindCardById_MustReturnOneCardsInstance() {
		when(bankAccountService.findCardById(anyLong()))
		.thenReturn(card);
		
		ResponseEntity<Cards> cardResponse = cardsController
				.findCardById(anyLong());
		
		assertNotNull(cardResponse);
		assertNotNull(cardResponse.getBody());
		assertEquals(ResponseEntity.class, cardResponse.getClass());
		assertEquals(Cards.class, cardResponse.getBody().getClass());
		assertEquals(HttpStatus.OK, cardResponse.getStatusCode());
		assertEquals(card.getId(), (cardResponse.getBody()).getId());

	}
	
	@Test
	void testFindCardById_MustReturnCardNotFoundException() {
		when(bankAccountService.findCardById(anyLong()))
		.thenThrow(new CardNotFoundException("Id not found, id: " + anyLong()));
		
		try {
			cardsController.findCardById(0L);
		} catch(Exception ex) {
			assertEquals( CardNotFoundException.class, ex.getClass());
			assertThat(ex.getMessage()).isEqualTo("Id not found, id: 0");
		}


	}

	@Test
	void testUpdateCard_MustReturnOneCardsInstance() {
		when(bankAccountService.findCardById(anyLong())).thenReturn(card);
		when(bankAccountService.updateCard(any(), any())).thenReturn(card);
		ResponseEntity<Cards> cardResponse = cardsController.updateCard(anyLong(), dtoCard);
		
		assertNotNull(cardResponse);
		assertNotNull(cardResponse.getBody());
		assertEquals(ResponseEntity.class, cardResponse.getClass());
		assertEquals(Cards.class, cardResponse.getBody().getClass());
		assertEquals(HttpStatus.OK, cardResponse.getStatusCode());
		assertEquals(card.getId(), (cardResponse.getBody()).getId());	
		}
	
	@Test
	void testUpdateCard_MustReturnCardNotFoundException() {
		when(bankAccountService.findCardById(anyLong()))
		.thenThrow(new CardNotFoundException("Id not found, id: " + anyLong()));
		
		try {
			cardsController.updateCard(0L, dtoCard);
		} catch(Exception ex) {
			assertEquals( CardNotFoundException.class, ex.getClass());
			assertThat(ex.getMessage()).isEqualTo("Id not found, id: 0");
		}

	}
	
	@Test
	void estUpdateCard_MustReturnDataIntegrityViolationException() {
		when(bankAccountService.findCardById(anyLong())).thenReturn(card);
		when(bankAccountService.updateCard(any(), any()))
		.thenThrow(new DataIntegrityViolationException("message"));
		
		try {
			cardsController.updateCard(anyLong(), dtoCard);
		} catch(Exception ex) {
			assertEquals( DataIntegrityViolationException.class, ex.getClass());
		}
		
	
	}

	@Test
	void testDeleteCardId_MustReturnStatusOK() {
		when(bankAccountService.findCardById(any())).thenReturn(card);
		
		ResponseEntity<Object> cardResponse = cardsController.deleteCardId(anyLong());
		
		assertNotNull(cardResponse);
		assertEquals(ResponseEntity.class, cardResponse.getClass());
		assertEquals(HttpStatus.OK, cardResponse.getStatusCode());
		verify(bankAccountService,times(1)).deleteCardId(anyLong());
	}

	@Test
	void testDeleteCardId_MustReturnCardNotFoundException() {		
		when(bankAccountService.findCardById(anyLong()))
		.thenThrow(new CardNotFoundException("Id not found, id: " + anyLong()));
		
		try {
			cardsController.deleteCardId(0L);
		
		} catch(Exception ex) {
			assertEquals( CardNotFoundException.class, ex.getClass());
			assertThat(ex.getMessage()).isEqualTo("Id not found, id: 0");
		}
		
	}
}
