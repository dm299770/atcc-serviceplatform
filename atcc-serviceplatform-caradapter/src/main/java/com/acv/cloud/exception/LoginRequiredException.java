package com.acv.cloud.exception;

public class LoginRequiredException extends Exception {

    public LoginRequiredException() {
    }

    public LoginRequiredException(String message) {
        super(message);
    }

    public LoginRequiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginRequiredException(Throwable cause) {
        super(cause);
    }

    public LoginRequiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
