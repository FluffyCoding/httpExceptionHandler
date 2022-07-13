package com.unity.httpexceptionhandler.exception;

import com.unity.httpexceptionhandler.model.HttpValidationResponse;
import com.unity.httpexceptionhandler.model.ValidatorResponseMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String VALIDATION_FAILED = "Data Validation occurred.";


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<HttpValidationResponse> onConstraintValidationException(HttpStatus status, ConstraintViolationException ex) {
        List<ValidatorResponseMessage> validatorResponseMessages = new ArrayList<>();
        for (ConstraintViolation violation : ex.getConstraintViolations()) {
            var violations = new ValidatorResponseMessage(violation.getPropertyPath().toString(), violation.getMessage());
            validatorResponseMessages.add(violations);
        }
        var error = new HttpValidationResponse(status.value(), status, status.getReasonPhrase().toUpperCase(), VALIDATION_FAILED, validatorResponseMessages);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }



    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        List<ValidatorResponseMessage> validatorResponseMessages = new ArrayList<>();
        List<String> details = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(
                (error) -> {
                    var errorDetails = new ValidatorResponseMessage(error.getField(), error.getDefaultMessage());
                    validatorResponseMessages.add(errorDetails);
                });
        var error = new HttpValidationResponse(status.value(), status, status.getReasonPhrase().toUpperCase(), VALIDATION_FAILED, validatorResponseMessages);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
