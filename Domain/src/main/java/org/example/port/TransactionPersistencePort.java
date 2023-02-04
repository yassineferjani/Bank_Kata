package org.example.port;

import org.example.data.TransactionDTO;

import java.util.List;

public interface TransactionPersistencePort {
    void add(TransactionDTO transactionDTO);
    List<TransactionDTO> getAll();
    TransactionDTO getById(Long id);
    void deleteById(Long id);
}