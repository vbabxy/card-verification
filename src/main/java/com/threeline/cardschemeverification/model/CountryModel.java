package com.threeline.cardschemeverification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryModel {

    private String emoji;

    private String latitude;

    private String alpha2;

    private String name;

    private String numeric;

    private String currency;

    private String longitude;
}
