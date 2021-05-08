package com.threeline.cardschemeverification.service.impl;

import com.threeline.cardschemeverification.client.CardVerificationClient;
import com.threeline.cardschemeverification.domain.CardDetails;
import com.threeline.cardschemeverification.exception.BadRequestException;
import com.threeline.cardschemeverification.exception.CardVerificationException;
import com.threeline.cardschemeverification.model.CardResponseModel;
import com.threeline.cardschemeverification.model.CardVerificationDto;
import com.threeline.cardschemeverification.model.CardVerificationResponse;
import com.threeline.cardschemeverification.repository.CardDetailsRepository;
import com.threeline.cardschemeverification.service.CardDetailsService;
import com.threeline.cardschemeverification.service.PayloadStatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CardDetailsServiceImpl implements CardDetailsService {

    private final CardDetailsRepository cardDetailsRepository;
    private final CardVerificationClient cardVerificationClient;

    public CardDetailsServiceImpl(CardDetailsRepository cardDetailsRepository, CardVerificationClient cardVerificationClient) {
        this.cardDetailsRepository = cardDetailsRepository;
        this.cardVerificationClient = cardVerificationClient;
    }

    @Override
    public CardVerificationResponse verifyCard(String cardNumber) {

        Optional<CardDetails> cardDetails = cardDetailsRepository.findByCardNumber(cardNumber);

        CardVerificationResponse cardVerificationResponse;

        if(cardDetails.isPresent()) {
            Long numberOfHits = cardDetails.get().getNumberOfHits() + 1;
            cardDetails.get().setNumberOfHits(numberOfHits);
            cardDetailsRepository.save(cardDetails.get());
            cardVerificationResponse = buildCardVerificationResponse(cardDetails.get().getBankName(),
                    cardDetails.get().getCardType(), cardDetails.get().getCardScheme());

        }else {
            CardResponseModel cardResponseModel = cardVerificationClient.cardLookup(cardNumber)
                    .orElseThrow(()-> new CardVerificationException("card number does not exist"));

            log.info("card response Model {} ", cardResponseModel);

            String bankName = "";

            if(cardResponseModel.getBank() != null) {
                bankName = cardResponseModel.getBank().getName();
            }

            cardDetailsRepository.save(CardDetails.builder()
                    .bankName(bankName)
                    .cardNumber(cardNumber)
                    .cardType(cardResponseModel.getType())
                    .cardScheme(cardResponseModel.getScheme())
                    .numberOfHits(1L)
                    .build());

            cardVerificationResponse = buildCardVerificationResponse(bankName,
                    cardResponseModel.getType(), cardResponseModel.getScheme());
        }

         return cardVerificationResponse;
    }

    private CardVerificationResponse buildCardVerificationResponse(String bankName, String cardType, String cardScheme) {

        CardVerificationDto cardVerificationDto = CardVerificationDto.builder()
                .bank(bankName)
                .type(cardType)
                .scheme(cardScheme)
                .build();

        CardVerificationResponse cardVerificationResponse = new CardVerificationResponse();
        cardVerificationResponse.setSuccess(true);
        cardVerificationResponse.setPayload(cardVerificationDto);

        return cardVerificationResponse;
    }

    @Override
    public PayloadStatResponse getNumberOfCardHits(int start, int limit) {
        log.info("about getting number of hits");

        int pageNumber = start -1;

        if(pageNumber < 0){
            throw new BadRequestException("start cannot be zero or less than zero");
        }

        Pageable pageable = PageRequest.of(pageNumber,limit);

        Page<CardDetails> cardDetails = cardDetailsRepository.findAllCards(pageable);

        Map<String, Long> result = cardDetails.getContent().stream()
                .collect(Collectors.toMap(CardDetails::getCardNumber,
                        CardDetails::getNumberOfHits));

        return PayloadStatResponse.builder()
                .limit(limit)
                .start(start)
                .size(cardDetails.getTotalElements())
                .payload(result)
                .build();
    }
}
