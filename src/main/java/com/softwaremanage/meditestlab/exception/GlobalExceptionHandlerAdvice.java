package com.softwaremanage.meditestlab.exception;

import com.softwaremanage.meditestlab.pojo.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandlerAdvice {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseMessage<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        System.out.println("捕获到异常: " + ex.getMessage());
        return ResponseMessage.error(ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseMessage<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        StringBuilder errorMessage = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            errorMessage.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()).append("\n");
        }
        return ResponseMessage.error(errorMessage.toString());
    }
    }
