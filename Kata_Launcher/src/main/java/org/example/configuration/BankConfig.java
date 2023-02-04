package org.example.configuration;


import org.example.adapters.AccountJpaAdapter;
import org.example.adapters.ClientJpaAdapter;
import org.example.adapters.TransactionJpaAdapter;
import org.example.port.*;
import org.example.service.AccountServiceImp;
import org.example.service.ClientServiceImp;
import org.example.service.CreditCardServiceImp;
import org.example.service.TransactionServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankConfig {
    @Bean
    public ClientPersistencePort clientPersistencePort(){
        return new ClientJpaAdapter();
    }

    @Bean
    public ClientServicePort clientServicePort(){
        return new ClientServiceImp(clientPersistencePort());
    }
    
    @Bean
    public AccountPersistencePort accountPersistencePort(){
        return new AccountJpaAdapter();
    }

    @Bean
    public AccountServicePort accountServicePort(){
        return new AccountServiceImp(accountPersistencePort(),clientServicePort());
    }
    
    @Bean
    public TransactionPersistencePort transactionPersistencePort(){
        return new TransactionJpaAdapter();
    }

    @Bean
    public TransactionServicePort transactionServicePort(){
        return new TransactionServiceImp(transactionPersistencePort(), accountServicePort());
    }

    @Bean
    public CreditCardServicePort creditCardServicePort(){
        return new CreditCardServiceImp(transactionServicePort(), accountServicePort(),transactionPersistencePort());
    }
}
