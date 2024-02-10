package com.Exceptions;

public class FileNotCorrectException extends RuntimeException{
    public FileNotCorrectException(String message) {
        super(message);
    }

    public FileNotCorrectException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNotCorrectException(Throwable cause) {
        super(cause);
    }

}
