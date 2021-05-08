package com.threeline.cardschemeverification.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppResponse {

    private boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object error;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int start;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int limit;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private long size;

    private Object payload;

}
