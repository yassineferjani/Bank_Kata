package AdaptersTest;

import org.example.adapters.TransactionJpaAdapter;
import org.example.data.TransactionDTO;
import org.example.entities.Transaction;
import org.example.mappers.TransactionMapper;
import org.example.repositories.TransactionDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TransactionJpaAdapterTest {

    @Mock
    private TransactionDAO transactionDAO;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionJpaAdapter transactionJpaAdapter;

    @Test
    public void testAddTransaction() {
        // setup
        TransactionDTO transactionDTO = new TransactionDTO();
        Transaction transaction = new Transaction();
        when(transactionMapper.getTransactionFromTransactionDTO(transactionDTO)).thenReturn(transaction);
        // execution
        transactionJpaAdapter.addTransaction(transactionDTO);
        // assertion
        verify(transactionDAO, times(1)).save(transaction);
    }

    @Test
    public void testGetAllTransactions() {
        // setup
        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);
        TransactionDTO transactionDTO1 = new TransactionDTO();
        TransactionDTO transactionDTO2 = new TransactionDTO();
        when(transactionDAO.findAll()).thenReturn(transactions);
        when(transactionMapper.getTransactionDTOFromTransaction(transaction1)).thenReturn(transactionDTO1);
        when(transactionMapper.getTransactionDTOFromTransaction(transaction2)).thenReturn(transactionDTO2);
        // execution
        List<TransactionDTO> result = transactionJpaAdapter.getAllTransactions();
        // assertion
        assertEquals(2, result.size());
        assertEquals(transactionDTO1, result.get(0));
        assertEquals(transactionDTO2, result.get(1));
    }

    @Test
    public void testGetTransactionById() {
        // setup
        Long id = 1L;
        Transaction transaction = new Transaction();
        TransactionDTO transactionDTO = new TransactionDTO();
        Optional<Transaction> optionalTransaction = Optional.of(transaction);
        when(transactionDAO.findById(id)).thenReturn(optionalTransaction);
        when(transactionMapper.getTransactionDTOFromTransaction(transaction)).thenReturn(transactionDTO);
        // execution
        TransactionDTO result = transactionJpaAdapter.getTransactionById(id);
        // assertion
        assertEquals(transactionDTO, result);
    }

    @Test
    public void testGetTransactionById_NotFound() {
        // setup
        Long id = 1L;
        Optional<Transaction> optionalTransaction = Optional.empty();
        when(transactionDAO.findById(id)).thenReturn(optionalTransaction);
        // execution
        TransactionDTO result = transactionJpaAdapter.getTransactionById(id);
        // assertion
        assertNull(result);
    }

    @Test
    public void testDeleteTransactionById() {
        // setup
        Long id = 1L;
        // execution
        transactionJpaAdapter.deleteTransactionById(id);
        // assertion
        verify(transactionDAO, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateTransaction() {
        testAddTransaction();
    }
}
