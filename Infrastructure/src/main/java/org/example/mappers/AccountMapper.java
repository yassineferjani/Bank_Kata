package org.example.mappers;

import java.util.Optional;

import org.example.data.AccountDTO;
import org.example.data.ClientDTO;
import org.example.entities.Account;
import org.example.entities.Client;
import org.example.port.AccountPersistencePort;
import org.example.port.ClientPersistencePort;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public class AccountMapper {

	public static AccountDTO getAccountDTOFromAccount (Account account) {
		return AccountDTO.builder()
				.rib(account.getRib())
				.balance(account.getBalance())
				.clientId(account.getClient().getId())
				.build();
	}
	
	public static Account getAccountFromAccountDTO (AccountDTO accountDTO, Client client) {
		return Account.builder()
				.rib(accountDTO.getRib())
				.balance(accountDTO.getBalance())
				.client(client)
				.build();
	}
	
}
