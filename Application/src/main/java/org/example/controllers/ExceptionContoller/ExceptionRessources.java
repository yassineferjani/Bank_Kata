package org.example.controllers.ExceptionContoller;

import org.example.exception.AccountNotFoundException;
import org.example.exception.ClientNotFoundException;
import org.example.exception.Insufficientfunds;
import org.example.exception.TransactionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionRessources {
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<Object> clientException (ClientNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Object> accountException (AccountNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<Object> transactionException (TransactionNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Insufficientfunds.class)
    public ResponseEntity<Object> fundsException (Insufficientfunds ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
