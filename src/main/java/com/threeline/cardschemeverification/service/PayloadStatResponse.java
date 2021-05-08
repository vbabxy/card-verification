package com.threeline.cardschemeverification.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayloadStatResponse {
    private long size;
    private int start;
    private int limit;
    private Map<String, Long> payload;
}
