package org.example.exception;

public class Insufficientfunds extends RuntimeException {
    private String message;
    public Insufficientfunds() {}
    public Insufficientfunds(String msg) {
        super(msg);
        this.message = msg;
    }
}

