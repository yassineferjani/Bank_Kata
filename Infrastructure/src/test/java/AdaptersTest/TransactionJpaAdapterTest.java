package AdaptersTest;

import org.example.adapters.TransactionJpaAdapter;
import org.example.data.AccountDTO;
import org.example.data.ClientDTO;
import org.example.data.TransactionDTO;
import org.example.entities.Account;
import org.example.entities.Transaction;
import org.example.mappers.TransactionMapper;
import org.example.port.AccountPersistencePort;
import org.example.port.ClientPersistencePort;
import org.example.repositories.AccountDAO;
import org.example.repositories.TransactionDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TransactionJpaAdapterTest {

    @Mock
    private TransactionDAO transactionDAO;

    @Mock
    private AccountDAO accountDAO;

    @Mock
    private ClientPersistencePort clientPersistencePort;

    @Mock
    private AccountPersistencePort accountPersistencePort;

    @InjectMocks
    private TransactionJpaAdapter transactionJpaAdapter;

    @Test
    public void testAddTransaction() {
        TransactionDTO transactionDTO = new TransactionDTO();
        AccountDTO accountDTO = new AccountDTO();
        ClientDTO clientDTO = new ClientDTO();
        Account account = new Account();
        Transaction transaction = new Transaction();

        when(accountPersistencePort.getById(transactionDTO.getAccountId())).thenReturn(accountDTO);
        when(clientPersistencePort.getById(accountDTO.getClientId())).thenReturn(clientDTO);
        when(transactionDAO.save(any(Transaction.class))).thenReturn(transaction);

        transactionJpaAdapter.add(transactionDTO);

        verify(transactionDAO, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testGetAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        List<TransactionDTO> expectedTransactions = new ArrayList<>();
        when(transactionDAO.findAll()).thenReturn(transactions);

        List<TransactionDTO> result = transactionJpaAdapter.getAll();

        verify(transactionDAO, times(1)).findAll();
        assertEquals(expectedTransactions, result);
    }

    @Test
    public void testGetTransactionById() {
        Long id = 1L;
        Transaction transaction = new Transaction();
        transaction.setAccount(new Account());
        TransactionDTO expectedTransaction = new TransactionDTO();
        Optional<Transaction> optionalTransaction = Optional.of(transaction);

        when(transactionDAO.findById(id)).thenReturn(optionalTransaction);

        TransactionDTO result = transactionJpaAdapter.getById(id);

        verify(transactionDAO, times(1)).findById(id);
        assertEquals(expectedTransaction, result);
    }

    @Test
    public void testGetTransactionById_NotFound() {
        // setup
        Long id = 1L;
        Optional<Transaction> optionalTransaction = Optional.empty();
        when(transactionDAO.findById(id)).thenReturn(optionalTransaction);
        // execution
        TransactionDTO result = transactionJpaAdapter.getById(id);
        // assertion
        assertNull(result);
    }

    @Test
    public void testDeleteTransactionById() {
        // setup
        Long id = 1L;
        // execution
        transactionJpaAdapter.deleteById(id);
        // assertion
        verify(transactionDAO, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateTransaction() {
        testAddTransaction();
    }
}
