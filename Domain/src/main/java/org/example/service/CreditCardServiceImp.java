package org.example.service;

import org.example.data.AccountDTO;
import org.example.data.TransactionDTO;
import org.example.data.TransactionType;
import org.example.exception.AccountNotFoundException;
import org.example.exception.Insufficientfunds;
import org.example.exception.TransactionNotFoundException;
import org.example.port.AccountServicePort;
import org.example.port.CreditCardServicePort;
import org.example.port.TransactionPersistencePort;
import org.example.port.TransactionServicePort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
@Service
public class CreditCardServiceImp implements CreditCardServicePort {
    private AccountServicePort accountServicePort;
    private TransactionPersistencePort transactionPersistencePort;

    public CreditCardServiceImp(AccountServicePort accountServicePort, TransactionPersistencePort transactionPersistencePort){
        this.accountServicePort = accountServicePort;
        this.transactionPersistencePort = transactionPersistencePort;
    }

    @Override
    public synchronized AccountDTO withdrawal(Long rib, double amount) {
        return accountServicePort.getById(rib)
                .filter(account -> account.getBalance() >= amount)
                .map(account -> {transactionPersistencePort.add(TransactionDTO.builder()
                        .accountId(rib)
                        .amount(amount)
                        .dateTransaction(new Date())
                        .transactionType(TransactionType.Withdrawal)
                        .oldBalance(accountServicePort.getById(rib).get().getBalance())
                        .newBalance(accountServicePort.getById(rib).get().getBalance() - amount)
                        .build());
                    account.setBalance(account.getBalance() - amount);
                    accountServicePort.update(account);
                    return account;
                })
                .orElseThrow(() -> new AccountNotFoundException("Account Not Found"));
    }

    @Override
    public synchronized AccountDTO deposit(Long rib, double amount) {
        return accountServicePort.getById(rib)
                .map(account -> {
                    transactionPersistencePort.add(TransactionDTO.builder()
                            .accountId(rib)
                            .amount(amount)
                            .dateTransaction(new Date())
                            .transactionType(TransactionType.Deposit)
                            .oldBalance(accountServicePort.getById(rib).get().getBalance())
                            .newBalance(accountServicePort.getById(rib).get().getBalance() + amount)
                            .build());
                    account.setBalance(account.getBalance() + amount);
                    accountServicePort.update(account);
                    return account;
                })
                .orElseThrow(() -> new AccountNotFoundException("Account Not Found"));
    }

    @Override
    public synchronized void deleteById(Long id) {
        TransactionDTO transactionDTO = transactionPersistencePort.getById(id);
        Optional.ofNullable(transactionDTO)
                .ifPresentOrElse(
                        t -> {
                            Long accountDTO = t.getAccountId();
                            if (t.getTransactionType() == TransactionType.Deposit) {
                                withdrawal(accountDTO, t.getAmount());
                            } else {
                                deposit(accountDTO, t.getAmount());
                            }
                        },
                        () -> {
                            throw new TransactionNotFoundException("Transaction Not Found");
                        });
    }

}
