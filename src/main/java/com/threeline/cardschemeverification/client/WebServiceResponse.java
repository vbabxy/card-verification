package com.threeline.cardschemeverification.client;


import org.apache.commons.lang3.builder.ToStringBuilder;


public class   WebServiceResponse {

    private int responseCode = -1;
    private String responseMessage;

    public int getResponseCode() {
        return this.responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return this.responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
