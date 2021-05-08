package com.threeline.cardschemeverification.service;

import com.threeline.cardschemeverification.exception.BadRequestException;
import com.threeline.cardschemeverification.model.RequestHeaderDto;
import com.threeline.cardschemeverification.utils.AppUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class AuthenticationService {

    private static final String AUTHORIZATION_PREFIX = "3line";

    @Value("${appKey}")
    private String appKey;

    @Value("${timestamp}")
    private String timestamp;

    public void authenticateHeader(RequestHeaderDto requestHeader) {

        if(StringUtils.isBlank(requestHeader.getAppKey()) ||
                StringUtils.isBlank(requestHeader.getTimestamp())
                || StringUtils.isBlank(requestHeader.getTimestamp())) {

            throw new BadRequestException("Invalid request sent to the server");
        }

        verifyHashParameter(requestHeader.getAuthorization());

    }

    private void verifyHashParameter(String authorization) {
        if(!authorization.startsWith(AUTHORIZATION_PREFIX)) {
            throw new BadRequestException("Invalid Authorization key");
        }

        String hashed = authorization.replace(AUTHORIZATION_PREFIX, "");
        verifyHash(hashed);

    }

    private void verifyHash(String hashed) {
        String pattern = new StringBuilder(appKey)
                .append(timestamp)
                .toString();
        String systemHash = "";
        try {
            systemHash =  AppUtil.computeHash(pattern);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

       if(!hashed.equalsIgnoreCase(systemHash)) {
           throw new BadRequestException("Invalid Authorization");
       }
    }
}
