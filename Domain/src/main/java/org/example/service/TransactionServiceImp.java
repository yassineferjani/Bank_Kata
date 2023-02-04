package org.example.service;

import org.example.data.AccountDTO;
import org.example.data.TransactionType;
import org.example.data.TransactionDTO;
import org.example.exception.AccountNotFoundException;
import org.example.exception.Insufficientfunds;
import org.example.exception.TransactionNotFoundException;
import org.example.port.AccountServicePort;
import org.example.port.CreditCardServicePort;
import org.example.port.TransactionPersistencePort;
import org.example.port.TransactionServicePort;

import java.util.*;
import java.util.stream.Collectors;

public class TransactionServiceImp implements TransactionServicePort {

    private TransactionPersistencePort transactionPersistencePort;
    private AccountServicePort accountServicePort;
    private CreditCardServicePort creditCardServicePort;


    public TransactionServiceImp(TransactionPersistencePort transactionPersistencePort,AccountServicePort accountServicePort){
        this.transactionPersistencePort=transactionPersistencePort;
        this.accountServicePort=accountServicePort;

    }

    @Override
    public List<TransactionDTO> getAll() {
        return transactionPersistencePort.getAll();
    }
    
    @Override
    public List<TransactionDTO> getByAccountId(Long id) {
        List<TransactionDTO> transactionDTOS = transactionPersistencePort.getAll().stream()
                .filter(t->t.getAccountId()==id)
                .collect(Collectors.toList());
        if (transactionDTOS.size()==0)
            throw new TransactionNotFoundException("No Transaction");
        return transactionDTOS;

    }

    @Override
    public TransactionDTO getById(Long id) {
        TransactionDTO transactionDTO = transactionPersistencePort.getById(id);
        if (transactionDTO==null)
            throw new TransactionNotFoundException("Transaction Not Found");
        return transactionPersistencePort.getById(id);
    }

}
