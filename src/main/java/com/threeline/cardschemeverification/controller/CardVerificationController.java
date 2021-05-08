package com.threeline.cardschemeverification.controller;

import com.threeline.cardschemeverification.model.AppResponse;
import com.threeline.cardschemeverification.model.CardVerificationResponse;
import com.threeline.cardschemeverification.model.RequestHeaderDto;
import com.threeline.cardschemeverification.service.AuthenticationService;
import com.threeline.cardschemeverification.service.CardDetailsService;
import com.threeline.cardschemeverification.service.PayloadStatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;

@RestController
@Slf4j
@RequestMapping("/api/v1/card-scheme")
public class CardVerificationController {

    private final CardDetailsService cardDetailsService;
    private final AuthenticationService authenticationService;

    public CardVerificationController(CardDetailsService cardDetailsService, AuthenticationService authenticationService) {
        this.cardDetailsService = cardDetailsService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/verify/{cardNumber}")
    public ResponseEntity<AppResponse> verifyCard(@RequestHeader(name = "appKey") String appKey,
                                                  @RequestHeader(name = "timeStamp") String timestamp,
                                                  @RequestHeader(name = "Authorization") String authorization,
                                                  @PathVariable("cardNumber") String cardNumber) {

        RequestHeaderDto requestHeader = RequestHeaderDto.builder()
                .appKey(appKey)
                .authorization(authorization)
                .timestamp(timestamp)
                .build();

        authenticationService.authenticateHeader(requestHeader);

        CardVerificationResponse cardVerificationResponse = cardDetailsService.verifyCard(cardNumber);

        AppResponse appResponse = AppResponse.builder()
                .success(cardVerificationResponse.isSuccess())
                .payload(cardVerificationResponse.getPayload())
                .build();

        return ResponseEntity.ok(appResponse);

    }

    @GetMapping(path = "/card-scheme/stats")
    public ResponseEntity<AppResponse> getNumberOfHits(@RequestParam("start") int start,
                                                       @RequestParam("limit") int limit) {

        PayloadStatResponse payloadStatResponse = cardDetailsService.getNumberOfCardHits(start, limit);


        return ResponseEntity.status(HttpStatus.OK)
                .body(AppResponse.builder()
                        .success(true)
                        .size(payloadStatResponse.getSize())
                        .limit(payloadStatResponse.getLimit())
                        .start(payloadStatResponse.getStart())
                        .payload(payloadStatResponse.getPayload())
                        .build());

    }
}
