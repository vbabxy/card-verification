package com.threeline.cardschemeverification.exception;



import com.threeline.cardschemeverification.model.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@Slf4j
@ControllerAdvice
public class ExceptionHandlers {


    @ExceptionHandler(CardVerificationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public AppResponse handleCardVerifcationException(final CardVerificationException ex) {
        log.error("Card Verification exception thrown");

        return AppResponse.builder()
                .payload("")
                .success(false)
                .error(ex.getMessage())
                .build();


    }




    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public AppResponse handleBadRequestException(final BadRequestException ex) {
        log.error("Bad Request exception thrown");

        return AppResponse.builder()
                .payload("")
                .success(false)
                .error(ex.getMessage())
                .build();


    }

}
