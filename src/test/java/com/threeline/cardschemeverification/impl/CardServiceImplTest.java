package com.threeline.cardschemeverification.impl;


import com.threeline.cardschemeverification.client.CardVerificationClient;
import com.threeline.cardschemeverification.exception.CardVerificationException;
import com.threeline.cardschemeverification.model.BankModel;
import com.threeline.cardschemeverification.model.CardResponseModel;
import com.threeline.cardschemeverification.model.CardVerificationResponse;
import com.threeline.cardschemeverification.model.CountryModel;
import com.threeline.cardschemeverification.repository.CardDetailsRepository;
import com.threeline.cardschemeverification.service.CardDetailsService;
import com.threeline.cardschemeverification.service.PayloadStatResponse;
import com.threeline.cardschemeverification.service.impl.CardDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CardServiceImplTest {

    @Mock
    CardVerificationClient cardVerificationClient;


    @Mock
    CardDetailsRepository cardDetailsRepository;

    CardDetailsService cardService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        cardService = new CardDetailsServiceImpl(cardDetailsRepository, cardVerificationClient);
    }

    @Test
    @DisplayName("Test Card Verification")
    void verifyCard() {

        String cardNumber = "539983";

        CountryModel countryModel = new CountryModel();
        countryModel.setAlpha2("NG");
        countryModel.setCurrency("NGN");
        countryModel.setLatitude("10");
        countryModel.setLongitude("8");
        countryModel.setName("Nigeria");
        countryModel.setNumeric("");

        BankModel bankModel = new BankModel();
        bankModel.setCity("");
        bankModel.setName("GTBANK");
        bankModel.setPhone("2348039003900");
        bankModel.setUrl("");


        CardResponseModel cardResponseModel = new CardResponseModel();
        cardResponseModel.setBank(bankModel);
        cardResponseModel.setBrand("Debit");
        cardResponseModel.setScheme("mastercard");
        cardResponseModel.setType("debit");
        cardResponseModel.setCountry(countryModel);


        when(cardVerificationClient.cardLookup(cardNumber)).thenReturn(Optional.of(cardResponseModel));

        CardVerificationResponse response = cardService.verifyCard(cardNumber);

        assertThat(response);

        verify(cardVerificationClient).cardLookup(cardNumber);
    }


    @Test
    @DisplayName("test card verification exception")
    void testCardVerificationException(){
        Assertions.assertThrows(CardVerificationException.class, ()->{
            cardService.verifyCard("10000");
        });
    }
}