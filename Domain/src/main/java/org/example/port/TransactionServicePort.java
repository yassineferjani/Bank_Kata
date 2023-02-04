package org.example.port;

import org.example.data.AccountDTO;
import org.example.data.TransactionDTO;

import java.util.List;

public interface TransactionServicePort {

        List<TransactionDTO> getAll();
        TransactionDTO getById(Long id);
        List<TransactionDTO> getByAccountId(Long id);
}
