package org.example.exception;

public class ClientNotFoundException extends RuntimeException {
    private String message;
    public ClientNotFoundException() {}
    public ClientNotFoundException(String msg) {
        super(msg);
        this.message = msg;
    }
}

