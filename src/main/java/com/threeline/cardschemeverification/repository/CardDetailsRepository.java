package com.threeline.cardschemeverification.repository;

import com.threeline.cardschemeverification.domain.CardDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface CardDetailsRepository extends JpaRepository<CardDetails, Long>, JpaSpecificationExecutor<CardDetails> {

    @Query("select c from card_details c where c.cardNumber=?1")
    Optional<CardDetails> findByCardNumber(String cardNumber);

    @Query("select c from card_details c")
    Page<CardDetails> findAllCards(Pageable pageable);
}
