package com.learn.springrest.exception;

public class HttpMessageNotReadableException extends RuntimeException {
    public HttpMessageNotReadableException(String message) {
        super(message);
    }
}
