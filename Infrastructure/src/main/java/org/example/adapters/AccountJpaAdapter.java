package org.example.adapters;

import org.example.data.AccountDTO;
import org.example.entities.Account;
import org.example.mappers.AccountMapper;
import org.example.mappers.ClientMapper;
import org.example.port.AccountPersistencePort;
import org.example.port.ClientPersistencePort;
import org.example.repositories.AccountDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@Transactional
public class AccountJpaAdapter implements AccountPersistencePort {

    private final AccountDAO accountDAO;
    private final ClientPersistencePort clientPersistencePort;

public AccountJpaAdapter(AccountDAO accountDAO,ClientPersistencePort clientPersistencePort){
    this.accountDAO = accountDAO;
    this.clientPersistencePort = clientPersistencePort;
}

    @Override
    public void add(AccountDTO accountDTO) {
        Account account = AccountMapper.getAccountFromAccountDTO(accountDTO, ClientMapper.getClientFromClientDTO(clientPersistencePort.getById(accountDTO.getClientId())));
        accountDAO.save(account);
    }

    @Override
    public List<AccountDTO> getAll() {
        List<Account> accounts = accountDAO.findAll();
        return accounts.stream().map(c->AccountMapper.getAccountDTOFromAccount(c)).collect(Collectors.toList());
    }

    @Override
    public AccountDTO getById(Long id) {
        Optional<Account> account = accountDAO.findById(id);
        return AccountMapper.getAccountDTOFromAccount(account.get());

    }

    @Override
    public void deleteById(Long id) {
        accountDAO.deleteById(id);
    }

    @Override
    public void update(AccountDTO accountDTO) {
        Account account = AccountMapper.getAccountFromAccountDTO(accountDTO, ClientMapper.getClientFromClientDTO(clientPersistencePort.getById(accountDTO.getClientId())));
        accountDAO.save(account);
    }
}
