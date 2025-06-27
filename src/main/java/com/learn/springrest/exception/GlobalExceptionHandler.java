package com.learn.springrest.exception;

import com.learn.springrest.utils.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    // Handle ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNotFound(ResourceNotFoundException ex) {
        logger.error("Resource not found: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(
                ApiResponse.error(ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        logger.error("Validation failed: {}", ex.getMessage(), ex);
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String msg = error.getDefaultMessage();
            errors.put(field, msg);
        });

        return new ResponseEntity<>(
                ApiResponse.error("Validation failed", errors),
                HttpStatus.BAD_REQUEST
        );
    }

    // Handle EmailAlreadyExistsException
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("email", ex.getMessage());
        return new ResponseEntity<>(ApiResponse.error("Validation failed", errors), HttpStatus.BAD_REQUEST);
    }

    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneric(Exception ex) {
        return new ResponseEntity<>(
                ApiResponse.error("Internal server error: " + ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    //
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(field, message);
        });
        return new ResponseEntity<>(ApiResponse.error("Validation failed", errors), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<String>> handleUnreadable(HttpMessageNotReadableException ex) {
        logger.error("Malformed JSON request: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(
                ApiResponse.error("Malformed JSON request"),
                HttpStatus.BAD_REQUEST
        );
    }
}