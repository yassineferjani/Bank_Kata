package TestServices;

import org.example.data.AccountDTO;
import org.example.data.TransactionDTO;
import org.example.data.TransactionType;
import org.example.port.AccountServicePort;
import org.example.port.CreditCardServicePort;
import org.example.port.TransactionPersistencePort;
import org.example.service.CreditCardServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreditCardServiceImpTest {

    @Mock
    private TransactionPersistencePort transactionPersistencePort;
    @Mock
    private AccountServicePort accountServicePort;

    @InjectMocks
    private CreditCardServiceImp creditCardServiceImp;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void testWithdrawal() {

        Long rib = 1L;
        double amount = 50.0;
        AccountDTO account = AccountDTO.builder().rib(rib).balance(100.0).build();

        when(accountServicePort.getById(rib)).thenReturn(Optional.of(account));

        AccountDTO updatedAccount = creditCardServiceImp.withdrawal(rib, amount);

        assertEquals(50.0, updatedAccount.getBalance());
    }

    @Test
    public void testDeposit() {

        Long rib = 1L;
        double amount = 50.0;
        AccountDTO account = AccountDTO.builder().rib(rib).balance(100.0).build();

        when(accountServicePort.getById(rib)).thenReturn(Optional.of(account));

        AccountDTO updatedAccount = creditCardServiceImp.deposit(rib, amount);

        assertEquals(150.0, updatedAccount.getBalance());
    }

    @Test
    public void testDeleteById() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setRib(1L);
        accountDTO.setBalance(100.0);
        TransactionDTO transactionDTO = new TransactionDTO(1L, TransactionType.Withdrawal,50.0,new Date(),100.0,50.0,1L);
        when(accountServicePort.getById(1L)).thenReturn(Optional.of(accountDTO));
        when(transactionPersistencePort.getById(1L)).thenReturn(transactionDTO);
        creditCardServiceImp.deleteById(1L);
        assertEquals(150, accountDTO.getBalance());
    }


}
