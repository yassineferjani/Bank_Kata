package TestServices;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.example.data.AccountDTO;
import org.example.data.TransactionType;
import org.example.data.TransactionDTO;
import org.example.port.AccountServicePort;
import org.example.port.CreditCardServicePort;
import org.example.port.TransactionPersistencePort;
import org.example.service.CreditCardServiceImp;
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
    private CreditCardServicePort creditCardServicePort;
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
        TransactionDTO t1 = new TransactionDTO(1L, TransactionType.Withdrawal,100.0,new Date(), 100.0,0.0, 1L);
        TransactionDTO t2 = new TransactionDTO(2L, TransactionType.Deposit,100.0,new Date(), 100.0,200.0, 1L);
        List<TransactionDTO> transactions = Arrays.asList(t1, t2);
        when(transactionPersistencePort.getAll()).thenReturn(transactions);
        List<TransactionDTO> result = transactionServiceImp.getByAccountId(1L);
        assertEquals(Arrays.asList(t1,t2), result);
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


}