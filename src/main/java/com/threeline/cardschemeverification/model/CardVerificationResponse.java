package com.threeline.cardschemeverification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardVerificationResponse {

    private CardVerificationDto payload;
    private boolean success;
}
