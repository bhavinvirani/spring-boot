package com.learn.springrest.exception;

import com.learn.springrest.utils.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // --- 1. Validation Errors (ApiResponse style) ---

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        log.error("Validation failed: {}", ex.getMessage(), ex);
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String msg = error.getDefaultMessage();
            errors.put(field, msg);
        });
        return ApiResponse.error("Validation failed", errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(field, message);
        });
        return ApiResponse.error("Constraint violation error:", errors);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ApiResponse<Map<String, String>> handleEmailExists(EmailAlreadyExistsException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("email", ex.getMessage());
        return ApiResponse.error("Email not found", errors);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiResponse<String> handleNotFound(ResourceNotFoundException ex) {
        log.error("Resource not found: {}", ex.getMessage(), ex);
        return ApiResponse.error(ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse<String> handleUnreadable(HttpMessageNotReadableException ex) {
        log.error("Malformed JSON request: {}", ex.getMessage(), ex);
        return ApiResponse.error("Malformed JSON request");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ApiResponse<String> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("Access denied: {}", ex.getMessage(), ex);
        return ApiResponse.error("Access denied: " + ex.getMessage());
    }


    // --- 2. Security and Generic Errors  ---
//    @ExceptionHandler(Exception.class)
//    public ProblemDetail handleSecurityExceptions(Exception ex) {
//        ex.printStackTrace(); //
//        ProblemDetail error = null;
//
//        if (ex instanceof BadCredentialsException) {
//            error = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
//            error.setProperty("description", "The username or password is incorrect");
//        } else if (ex instanceof AccountStatusException) {
//            error = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
//            error.setProperty("description", "The account is locked or disabled");
//        } else if (ex instanceof AccessDeniedException) {
//            error = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
//            error.setProperty("description", "You are not authorized to access this resource");
//        } else if (ex instanceof SignatureException) {
//            error = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
//            error.setProperty("description", "The JWT signature is invalid");
//        } else if (ex instanceof ExpiredJwtException) {
//            error = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
//            error.setProperty("description", "The JWT token has expired");
//        } else {
//            error = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
//            error.setProperty("description", "Unexpected internal error occurred");
//        }
//
//        return error;
//    }
}