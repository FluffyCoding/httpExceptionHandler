package com.unity.httpexceptionhandler.exception;

import com.unity.httpexceptionhandler.exception.domain.EmailExistException;
import com.unity.httpexceptionhandler.exception.domain.UsernameExistException;
import com.unity.httpexceptionhandler.model.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler implements ErrorController {

    private static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a '%s' request";
    private static final String EMPLOYEE_EXISTS = "Employee Name Already Exist, please select another Name";
    private static final String EMAIL_EXISTS = "Employee email Already Exist, please select another email address";


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
    }


    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<HttpResponse> methodNotSupportedException(EmailExistException exception) {
        return createHttpResponse(CONFLICT, EMAIL_EXISTS);
    }

    @ExceptionHandler(UsernameExistException.class)
    public ResponseEntity<HttpResponse> methodNotSupportedException(UsernameExistException exception) {
        return createHttpResponse(CONFLICT, EMPLOYEE_EXISTS);
    }


    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(
                new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                        message), httpStatus);
    }

}
