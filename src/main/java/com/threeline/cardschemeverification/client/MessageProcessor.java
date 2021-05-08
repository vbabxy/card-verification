package com.threeline.cardschemeverification.client;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@Slf4j
public final class MessageProcessor {

    private MessageProcessor() {
    }



    public WebServiceResponse sendHttpsRequest2(String serviceRequest) throws IOException {

        String result;
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        WebServiceResponse httpResponse = new WebServiceResponse();

        try {
            log.debug("sending http request with EndPoint URL : " + serviceRequest);
            log.debug("Finished disabling SSL Verification");

            RequestConfig.Builder requestBuilder = RequestConfig.custom();

            log.debug("Request Builder {} ", requestBuilder);

            HttpClientBuilder builder = HttpClientBuilder.create();
            builder.setDefaultRequestConfig(requestBuilder.build());


            httpClient = builder.build();

            HttpGet httpGet = new HttpGet(serviceRequest);

            response = httpClient.execute(httpGet);

            // Get hold of the response entity
            HttpEntity entity = response.getEntity();

            log.debug("Entity {} ", entity);

            String reason =response.getStatusLine().getReasonPhrase();
            log.debug("Reason {} ", reason);

            int statusCode = response.getStatusLine().getStatusCode();
            log.debug("executed Security restful request with response code : " + statusCode);
            httpResponse.setResponseCode(statusCode);

            if (entity != null) {
                result = EntityUtils.toString(entity);

                httpResponse.setResponseMessage(result);
                EntityUtils.consume(entity);
            }
            log.debug("\n the response from Custom FI Security check restful web service \n\n" + httpResponse.getResponseMessage() + "\n");
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException ex) {
                log.error(" A fatal IOException has occurred {}", ex.getMessage());
            }
        }
        return httpResponse;
    }


}