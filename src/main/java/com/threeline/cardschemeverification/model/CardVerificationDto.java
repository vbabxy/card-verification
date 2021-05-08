package com.threeline.cardschemeverification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardVerificationDto implements Serializable {

    private String scheme;
    private String type;
    private String bank;
}
