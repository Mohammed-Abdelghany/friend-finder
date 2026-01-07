package com.example.friendfinder.config.exception;

import com.example.friendfinder.helper.MessageResponse;
import com.example.friendfinder.service.bundelmessage.BundleMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalException {
    private final BundleMessageService bundleMessageService;
    @Autowired
    public GlobalException(BundleMessageService bundleMessageService) {
        this.bundleMessageService = bundleMessageService;
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse> handleAllExceptions(Exception ex) {

        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bundleMessageService.getEnMessage( ex.getMessage()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<MessageResponse>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<MessageResponse> errors = new ArrayList<>();
        ex.getFieldErrors().forEach(err ->
                errors.add(bundleMessageService.getEnMessage(err.getDefaultMessage())));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}





