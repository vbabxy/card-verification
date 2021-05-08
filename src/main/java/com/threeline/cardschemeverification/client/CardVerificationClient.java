package com.threeline.cardschemeverification.client;

import com.google.gson.Gson;
import com.threeline.cardschemeverification.model.CardResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class CardVerificationClient {

    @Value("${card.lookup.url}")
    private String cardLookUpUrl;

    private MessageProcessor messageProcessor;


    private Gson gson;

    public CardVerificationClient(MessageProcessor messageProcessor, Gson gson) {
        this.messageProcessor = messageProcessor;
        this.gson = gson;
    }

    public Optional<CardResponseModel> cardLookup(String cardNumber) {

        String lookupUrl = cardLookUpUrl + cardNumber;
        WebServiceResponse webServiceResponse = null;

        try {
            webServiceResponse = messageProcessor.sendHttpsRequest2(lookupUrl);
        } catch (IOException e) {
            log.error("connection error to service {} ", e.getMessage());
        }

        if (webServiceResponse != null) {

            if (webServiceResponse.getResponseCode() == 200) {
                CardResponseModel cardResponseModel = gson.fromJson(webServiceResponse.getResponseMessage(),
                        CardResponseModel.class);

                return Optional.of(cardResponseModel);
            }
        }
        return Optional.empty();
    }
}
