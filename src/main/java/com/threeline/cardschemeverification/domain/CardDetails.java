package com.threeline.cardschemeverification.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity(name="card_details")
@Data
@Builder
@AllArgsConstructor
public class CardDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="number_of_hits")
    private Long numberOfHits;

    @Column(name="card_type", length=20)
    private String cardType;

    @Column(name="card_scheme", length=45)
    private String cardScheme;

    @Column(name="bank_name", length=45)
    private String bankName;

    @Column(name ="card_number")
    private String cardNumber;

    public CardDetails() {
    }
}
