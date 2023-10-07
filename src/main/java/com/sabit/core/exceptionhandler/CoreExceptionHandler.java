package com.sabit.core.exceptionhandler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CoreExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleExceptions(Exception ex, WebRequest request) {
        ex.printStackTrace();
        log.error("ControllerAdvice -> ExceptionHandler -> ", ex, request);
        ExceptionResponse exceptionResponse;

        if (ex instanceof CoreException) {
            exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage());
        } else if (ex instanceof ObjectOptimisticLockingFailureException) {
            exceptionResponse = new ExceptionResponse(new Date(), "İşlem yapmak istediğiniz data bir başkası tarafından değiştirilmiş!");
        } else {
            exceptionResponse = new ExceptionResponse(new Date(), "Sistemde beklenmedik bir hata meydana geldi!");
        }
        return new ResponseEntity<>(exceptionResponse, HttpStatus.EXPECTATION_FAILED);
    }
}
