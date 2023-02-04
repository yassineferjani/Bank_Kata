package org.example.port;

import org.example.data.AccountDTO;
import org.example.data.TransactionDTO;

import java.util.List;

public interface AccountServicePort {

        void add(AccountDTO accountDTO);
        List<AccountDTO> getAll();
        AccountDTO getById(Long id);
        void deleteById(Long id);
        void update(AccountDTO accountDTO);
}
