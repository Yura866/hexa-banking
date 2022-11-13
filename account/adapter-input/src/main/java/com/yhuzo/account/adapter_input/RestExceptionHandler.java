package com.yhuzo.account.adapter_input;

import com.yhuzo.account.common.dto.ErrorV1;
import com.yhuzo.account.common.exception.ModelNotFoundException;
import com.yhuzo.account.common.exception.ThresholdExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ThresholdExceededException.class})
    public ErrorV1 handle(ThresholdExceededException exception) {
        return new ErrorV1(ErrorV1.ErrorCode.BAD_REQUEST, exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorV1 handle(ConstraintViolationException exception) {
        return new ErrorV1(ErrorV1.ErrorCode.BAD_REQUEST, exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ModelNotFoundException.class)
    public ErrorV1 handle(ModelNotFoundException exception) {
        return new ErrorV1(ErrorV1.ErrorCode.MODEL_NOT_FOUND, exception.getMessage());
    }

}
