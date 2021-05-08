package com.threeline.cardschemeverification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CardResponseModel {

    private CountryModel country;
    private BankModel bank;
    private String scheme;
    private String type;
    private String brand;
}
