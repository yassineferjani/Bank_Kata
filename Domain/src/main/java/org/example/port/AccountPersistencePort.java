package org.example.port;

import org.example.data.AccountDTO;

import java.util.List;

public interface AccountPersistencePort {

    void add(AccountDTO accountDTO);
    List<AccountDTO> getAll();
    AccountDTO getById(Long id);
    void deleteById(Long id);
    void update(AccountDTO accountDTO);
}
