package com.threeline.cardschemeverification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankModel {

    private String phone;

    private String city;

    private String name;

    private String url;
}
