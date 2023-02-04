package org.example.service;

import org.example.data.AccountDTO;
import org.example.exception.AccountNotFoundException;
import org.example.exception.ClientNotFoundException;
import org.example.port.AccountPersistencePort;
import org.example.port.AccountServicePort;
import org.example.port.ClientServicePort;

import java.util.List;

public class AccountServiceImp implements AccountServicePort {
    private AccountPersistencePort accountPersistencePort;
    private ClientServicePort clientServicePort;

    public AccountServiceImp(AccountPersistencePort accountPersistencePort, ClientServicePort clientServicePort){
        this.accountPersistencePort=accountPersistencePort;
        this.clientServicePort = clientServicePort;
    }

    @Override
    public void add(AccountDTO accountDTO) {
        if (accountDTO.getClientId()==null)
            throw new ClientNotFoundException("Client not found. An account must be associated with a valid client.");
        clientServicePort.getById(accountDTO.getClientId());
        accountPersistencePort.add(accountDTO);
    }

    @Override
    public List<AccountDTO> getAll() {
        return accountPersistencePort.getAll();
    }

    @Override
    public AccountDTO getById(Long id) {
        AccountDTO accountDTO = accountPersistencePort.getById(id);
        if (accountDTO==null)
            throw new AccountNotFoundException("Account Not Found");
        return accountDTO;
    }

    @Override
    public void deleteById(Long id) {
       getById(id);
       accountPersistencePort.deleteById(id);
    }

    @Override
    public void update(AccountDTO accountDTO) {
        accountPersistencePort.update(accountDTO);
    }

}
