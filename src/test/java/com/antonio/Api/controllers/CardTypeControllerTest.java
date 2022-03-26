package com.antonio.Api.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
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
import com.antonio.Api.services.exceptions.CardTypeNotFoundException;

@SpringBootTest
class CardTypeControllerTest {
	
	@InjectMocks
	private CardTypeController cardTypeController;
	
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
		cardTypeController = new CardTypeController(bankAccountService);

	}

	@Test
	void testCreateCardType_MustReturnOneCardTypeInstance() {
		when(bankAccountService.createCardType(any())).thenReturn(cardType);
		ResponseEntity<CardType> cardTypeResponse= cardTypeController.createCardType(dtoCardType);
		
		assertNotNull(cardTypeResponse);
		assertNotNull(cardTypeResponse.getHeaders().get("Location"));
		assertEquals(ResponseEntity.class, cardTypeResponse.getClass());
		assertEquals(HttpStatus.CREATED, cardTypeResponse.getStatusCode());
		
	}

	@Test
	void testFindCardTypeById_MustReturnOneCardTypeInstance() {
		when(bankAccountService.findCardTypeId(anyInt())).thenReturn(cardType);
		
		ResponseEntity<CardType> cardTypeResponse = cardTypeController.findCardTypeById(anyInt());
		
		assertNotNull(cardTypeResponse);
		assertNotNull(cardTypeResponse.getBody());
		assertEquals(ResponseEntity.class, cardTypeResponse.getClass());
		assertEquals(CardType.class, cardTypeResponse.getBody().getClass());
		assertEquals(HttpStatus.OK, cardTypeResponse.getStatusCode());
		assertEquals(cardType.getId(), (cardTypeResponse.getBody()).getId());	
		
	}
	
	@Test
	void testFindCardTypeById_MustReturnCardTypeNotFoundException() {
		when(bankAccountService.findCardTypeId(anyInt()))
		.thenThrow(new CardTypeNotFoundException("Id not found, id: " + anyLong()));
		
		try {
			cardTypeController.findCardTypeById(0);
		} catch(Exception ex) {
			assertEquals( CardTypeNotFoundException.class, ex.getClass());
			assertThat(ex.getMessage()).isEqualTo("Id not found, id: 0");
		}	
		
	}

	@Test
	void testDeleteCardTypeId_MustReturnStatusOK() {
		cardType.getCard().remove(0);
		when(bankAccountService.findCardTypeId(anyInt()))
		.thenReturn(cardType);
		doNothing().when(bankAccountService).deleteCardTypeId(anyInt());
		
		ResponseEntity<Object> cardTypeResponse = cardTypeController
				.deleteCardTypeId(anyInt());
				
		
		assertNotNull(cardTypeResponse);
		assertEquals(ResponseEntity.class, cardTypeResponse.getClass());
		assertEquals(HttpStatus.OK, cardTypeResponse.getStatusCode());
		verify(bankAccountService,times(1)).deleteCardTypeId(anyInt());
	}
	@Test
	void testDeleteCardTypeId__MustReturnStatusFORBIDDEN() {
		bankAccount.getCards().add(card);
		when(bankAccountService.findCardTypeId(anyInt()))
		.thenReturn(cardType);
		doNothing().when(bankAccountService).deleteCardTypeId(anyInt());
		
		ResponseEntity<Object> cardTypeResponse = cardTypeController
				.deleteCardTypeId(anyInt());
		
		assertNotNull(cardTypeResponse);
		assertEquals(ResponseEntity.class, cardTypeResponse.getClass());
		assertEquals(HttpStatus.FORBIDDEN, cardTypeResponse.getStatusCode());
	}
	
	@Test
	void testDeleteCardTypeId_MustReturnCardTypeNotFoundException() {
		
		when(bankAccountService.findCardTypeId(anyInt()))
		.thenThrow(new CardTypeNotFoundException("Id not found, id: " + anyLong()));
		
		try {
			cardTypeController.deleteCardTypeId(0);		
			
		} catch(Exception ex) {
			assertEquals( CardTypeNotFoundException.class, ex.getClass());
			assertThat(ex.getMessage()).isEqualTo("Id not found, id: 0");
		}	
		
	}

}

