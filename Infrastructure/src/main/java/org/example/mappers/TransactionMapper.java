package org.example.mappers;

import org.example.data.AccountDTO;
import org.example.data.ClientDTO;
import org.example.data.TransactionDTO;
import org.example.entities.Account;
import org.example.entities.Transaction;
import org.example.port.AccountPersistencePort;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public  class TransactionMapper {
	private  AccountPersistencePort accountPersistencePort;
	private AccountMapper accountMapper;


	public static TransactionDTO getTransactionDTOFromTransaction (Transaction transaction) {
		return TransactionDTO.builder()
				.id(transaction.getId())
				.transactionType(transaction.getTransactionType())
				.dateTransaction(transaction.getDateTransaction())
				.newBalance(transaction.getNewBalance())
				.oldBalance(transaction.getOldBalance())
				.amount(transaction.getAmount())
				.accountId(transaction.getAccount().getRib())
				.build();
	}
	
	public static Transaction getTransactionFromTransactionDTO (TransactionDTO transactionDTO, Account account) {
		return new Transaction(transactionDTO.getId(),transactionDTO.getTransactionType(),transactionDTO.getAmount(),
				transactionDTO.getDateTransaction(),transactionDTO.getNewBalance(),transactionDTO.getOldBalance(),
				account);
	}
}
