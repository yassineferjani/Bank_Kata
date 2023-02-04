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

import java.util.Date;

public class CreditCardServiceImp implements CreditCardServicePort {
    private TransactionServicePort transactionServicePort;
    private AccountServicePort accountServicePort;
    private TransactionPersistencePort transactionPersistencePort;

    public CreditCardServiceImp(TransactionServicePort transactionServicePort, AccountServicePort accountServicePort, TransactionPersistencePort transactionPersistencePort){
        this.accountServicePort = accountServicePort;
        this.transactionServicePort = transactionServicePort;
        this.transactionPersistencePort = transactionPersistencePort;
    }

    @Override
    public AccountDTO withdrawal(Long rib, double amount) {
        AccountDTO account = accountServicePort.getById(rib);

        if (account == null) {
            throw new AccountNotFoundException("Account Not Found");
        }
        else {
            if(account.getBalance()>= amount){

                TransactionDTO transaction = new TransactionDTO();
                transaction.setOldBalance(account.getBalance());
                transaction.setNewBalance(account.getBalance()-amount);
                transaction.setDateTransaction(new Date());
                transaction.setAmount(amount);
                transaction.setTransactionType(TransactionType.Withdrawal);
                transaction.setAccountId(account.getRib());
                account.setBalance(account.getBalance()-amount);
                transactionPersistencePort.add(transaction);
                accountServicePort.update(account);

                return account;
            }
            else
                throw new Insufficientfunds("Insufficient funds");
        }
    }

    @Override
    public AccountDTO deposit(Long rib, double amount) {
        AccountDTO account = accountServicePort.getById(rib);
        if (account == null) {
            throw new AccountNotFoundException("Account Not Found");
        }
        else {
            TransactionDTO transaction = new TransactionDTO();
            transaction.setOldBalance(account.getBalance());
            transaction.setNewBalance(account.getBalance()+amount);
            transaction.setDateTransaction(new Date());
            transaction.setAmount(amount);
            transaction.setTransactionType(TransactionType.Deposit);
            transaction.setAccountId(account.getRib());
            account.setBalance(account.getBalance() + amount);
            transactionPersistencePort.add(transaction);
            accountServicePort.update(account);
            return account;
        }
    }

    @Override
    public void deleteById(Long id) {
        TransactionDTO transactionDTO = transactionPersistencePort.getById(id);
        if (transactionDTO != null){
            Long accountDTO = transactionDTO.getAccountId();
            if (transactionDTO.getTransactionType()== TransactionType.Deposit){
                withdrawal(accountDTO,transactionDTO.getAmount());
            }else {
                deposit(accountDTO,transactionDTO.getAmount());
            }
        }else {
            throw  new TransactionNotFoundException("Transaction Not Found");
        }
    }

}
