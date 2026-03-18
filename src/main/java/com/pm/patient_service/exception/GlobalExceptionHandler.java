package com.pm.patient_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationException(MethodArgumentNotValidException ex){
        Map<String,String> errors=new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error->{
            errors.put(error.getField(),error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }

     @ExceptionHandler(EmailAlreadyExitsException.class)
      public ResponseEntity<Map<String,String>> handleEmailAlreadyExistsException(EmailAlreadyExitsException ex){
         Map<String,String> error=new HashMap<>();
         log.error("Email already exists: {}", ex.getMessage());
         error.put("message","Email already exists");
         return ResponseEntity.badRequest().body(error);
         }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String,String>> handlePatientNotFoundExceptionException(PatientNotFoundException ex){
        Map<String,String> error=new HashMap<>();
        log.error("Patient not found exception: {}", ex.getMessage());
        error.put("message","Patient not found exception");
        return ResponseEntity.badRequest().body(error);
    }


}
