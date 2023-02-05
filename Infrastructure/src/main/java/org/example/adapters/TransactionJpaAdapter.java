package org.example.adapters;

import org.example.data.AccountDTO;
import org.example.data.TransactionDTO;
import org.example.entities.Account;
import org.example.entities.Client;
import org.example.entities.Transaction;
import org.example.mappers.AccountMapper;
import org.example.mappers.ClientMapper;
import org.example.mappers.TransactionMapper;
import org.example.port.AccountPersistencePort;
import org.example.port.ClientPersistencePort;
import org.example.port.TransactionPersistencePort;
import org.example.repositories.AccountDAO;
import org.example.repositories.TransactionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@Transactional
public class TransactionJpaAdapter implements TransactionPersistencePort {
	
	@Autowired
    private TransactionDAO transactionDAO;
	@Autowired
	private AccountDAO accountDAO;
    @Autowired
    private ClientPersistencePort clientPersistencePort;
    @Autowired
    private AccountPersistencePort accountPersistencePort;



    @Override
    public void add(TransactionDTO transactionDTO) {
        AccountDTO account = accountPersistencePort.getById(transactionDTO.getAccountId());
        transactionDAO.save(TransactionMapper.getTransactionFromTransactionDTO(transactionDTO,
                AccountMapper.getAccountFromAccountDTO(account,ClientMapper.getClientFromClientDTO(
                        clientPersistencePort.getById(account.getClientId())))));
    }

    @Override
    public List<TransactionDTO> getAll() {

        return transactionDAO.findAll().stream()
                .map(t-> TransactionMapper.getTransactionDTOFromTransaction(t))
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDTO getById(Long id) {
        Optional<Transaction> transaction = transactionDAO.findById(id);
        return TransactionMapper.getTransactionDTOFromTransaction(transaction.get());
    }

    @Override
    public void deleteById(Long id) {
        transactionDAO.deleteById(id);
    }


}
