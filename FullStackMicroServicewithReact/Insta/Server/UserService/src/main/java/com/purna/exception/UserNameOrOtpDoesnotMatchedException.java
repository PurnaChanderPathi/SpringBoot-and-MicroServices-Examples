package com.purna.exception;

public class UserNameOrOtpDoesnotMatchedException extends RuntimeException {
    public UserNameOrOtpDoesnotMatchedException(String message) {
        super(message);
    }
}
