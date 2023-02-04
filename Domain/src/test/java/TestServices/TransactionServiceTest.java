package TestServices;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.example.data.AccountDTO;
import org.example.data.TransactionType;
import org.example.data.TransactionDTO;
import org.example.port.AccountServicePort;
import org.example.port.TransactionPersistencePort;
import org.example.service.TransactionServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class TransactionServiceTest {
    @Mock
    private TransactionPersistencePort transactionPersistencePort;
    @Mock
    private AccountServicePort accountServicePort;

    @InjectMocks
    private TransactionServiceImp transactionServiceImp;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAll() {
        List<TransactionDTO> transactions = Arrays.asList(new TransactionDTO(), new TransactionDTO());
        when(transactionPersistencePort.getAll()).thenReturn(transactions);
        List<TransactionDTO> result = transactionServiceImp.getAll();
        verify(transactionPersistencePort).getAll();
        assertEquals(transactions, result);
    }
    @Test
    public void testGetByAccountId() {
        List<TransactionDTO> transactions = Arrays.asList(new TransactionDTO(1L,TransactionType.Deposit,10.0,new Date(),90.0,100.0,1L), new TransactionDTO());
        when(transactionPersistencePort.getAll()).thenReturn(transactions);
        List<TransactionDTO> result = transactionServiceImp.getByAccountId(1L);
        verify(transactionPersistencePort).getAll();
        assertEquals(transactions, result);
    }
    
    @Test
    public void testGetById() {
        Long transactionId = 1L;
        TransactionDTO expectedTransaction = new TransactionDTO();
        expectedTransaction.setId(transactionId);
        when(transactionPersistencePort.getById(transactionId)).thenReturn(expectedTransaction);
        TransactionDTO result = transactionServiceImp.getById(transactionId);
        assertEquals(result,expectedTransaction);
    }

    @Test
    public void testDeleteById() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setRib(1L);
        accountDTO.setBalance(100.0);
        TransactionDTO transactionDTO = new TransactionDTO(1L, TransactionType.Withdrawal,50.0,new Date(),100.0,50.0,1L);
        when(accountServicePort.getById(1L)).thenReturn(accountDTO);
        when(transactionPersistencePort.getById(1L)).thenReturn(transactionDTO);
        transactionServiceImp.deleteById(1L);
        assertEquals(150, accountDTO.getBalance());
    }

    @Test
    public void testWithdrawal() {
        AccountDTO account = new AccountDTO();
        account.setRib(1L);
        account.setBalance(100);
        when(accountServicePort.getById(1L)).thenReturn(account);
        AccountDTO result = transactionServiceImp.withdrawal(1L, 50);
        verify(accountServicePort).getById(1L);
        verify(accountServicePort).update(account);
        assertEquals(50, account.getBalance(), 0);
        assertEquals(account, result);
    }

    @Test
    public void testDeposit() {
        AccountDTO account = new AccountDTO();
        account.setRib(1L);
        account.setBalance(100);
        when(accountServicePort.getById(1L)).thenReturn(account);
        AccountDTO result = transactionServiceImp.deposit(1L, 50);
        verify(accountServicePort).getById(1L);
        verify(accountServicePort).update(account);
        assertEquals(150, account.getBalance(), 0);
        assertEquals(account, result);
    }

}