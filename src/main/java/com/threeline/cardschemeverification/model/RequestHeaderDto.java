package com.threeline.cardschemeverification.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class RequestHeaderDto {

    @NotBlank(message = "app key must be present")
    private String appKey;
    @NotBlank(message = "timestamp must be present")
    private String timestamp;
    @NotBlank(message = "authorization be present")
    private String authorization;
}
