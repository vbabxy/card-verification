package com.threeline.cardschemeverification.service;

import com.threeline.cardschemeverification.model.CardVerificationResponse;

import java.util.Optional;

public interface CardDetailsService {

    CardVerificationResponse verifyCard(String cardNumber);

    PayloadStatResponse getNumberOfCardHits(int start, int limit);

}
