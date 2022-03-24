package com.antonio.Api.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
	Optional <Cards> cardOptional;
	


	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		startModels();
		bankAccountService = new BankAccountService(cardsRepository, 
				cardTypeRepository,bankAccountRepository);

	
}

	
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

	
		
	}
	

	@Test
	void testCreateBankAccount_ItMustSaveAnInstanceOfBankAccount() {
		
		//given
		BankAccount bankAccount1 = new BankAccount();
		bankAccount1 = bankAccount;
		
		//when
		bankAccountService.createBankAccount(dtoBankAccount);
		
		//then
		ArgumentCaptor<BankAccount> bankAccoutArgCaptor = 
				ArgumentCaptor.forClass(BankAccount.class);
		
		verify(bankAccountRepository).save(bankAccoutArgCaptor.capture());
		BankAccount capturedBankAccount = bankAccoutArgCaptor.getValue();
		assertThat(capturedBankAccount.getClass()).isEqualTo(bankAccount1.getClass());
		assertNotNull(capturedBankAccount);
		
	}
		

	@Test
	void testCreateCardType_ItMustSaveAnInstanceOfBankAccount() {
		//given
		CardType cardtype1 = new CardType();
		cardtype1 = cardType;
		
		//when
		bankAccountService.createCardType(dtoCardType);
		
		//then
		ArgumentCaptor<CardType>CardTypeArgCaptor = 
				ArgumentCaptor.forClass(CardType.class);
		
		verify(cardTypeRepository).save(CardTypeArgCaptor.capture());
		CardType capturedCardType = CardTypeArgCaptor.getValue();
		assertThat(capturedCardType.getClass()).isEqualTo(cardtype1.getClass());
		assertNotNull(capturedCardType);
		
		
	}

	@Test
	void testAddCardToBankAccount_ItMustSaveAnInstanceOfBankAccount() {
		//given
		BankAccount bankAccount1 = new BankAccount();
		bankAccount1 = bankAccount;
		
		//when
		bankAccountService.AddCardToBankAccount(dtoCard,bankAccount1);
		
		//then
		ArgumentCaptor<BankAccount> bankAccoutArgCaptor = 
				ArgumentCaptor.forClass(BankAccount.class);
		
		verify(bankAccountRepository).save(bankAccoutArgCaptor.capture());
		BankAccount capturedBankAccount = bankAccoutArgCaptor.getValue();
		assertThat(capturedBankAccount.getClass()).isEqualTo(bankAccount1.getClass());
		assertNotNull(capturedBankAccount);
		
	}

	@Test
	void cantGetAllBankAccounts_ItMustCheckIfRepositorysFuntionIsFindAll() {
		//when
		bankAccountService.findAll();
		//then
		verify(bankAccountRepository).findAll();
	}

//	@Test
//	void testFindCardById_WhenFindByIdItMustReturnCardsInstance() {
//		//given
//		Cards savedCard = cardsRepository.save(card);
//		//when
//		Cards expected = bankAccountService.findCardById(savedCard.getId());
//		
//		assertThat(expected).isNotNull();
//		
//	}
//
//	@Test
//	void testFindBankAccountId() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFindCardTypeId() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testUpdateBankAccount() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testUpdateCard() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDeleteBankAccountId() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDeleteCard() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDeleteCardTypeId() {
//		fail("Not yet implemented");
//	}
//
}
