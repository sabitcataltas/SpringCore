package com.sabit.core.exceptionhandler;

public class CoreException extends RuntimeException {
    public CoreException(String message) {
        super(message);
    }

    public static CoreException thrw(String message) {
        return new CoreException(message);
    }
}
