package MappersTest;
import org.example.data.TransactionDTO;
import org.example.entities.Account;
import org.example.entities.Transaction;
import org.example.mappers.TransactionMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TransactionMapperTest {
	@Mock
	private TransactionMapper transactionMapper;

	@BeforeEach
	public void setUp() {
		transactionMapper = new TransactionMapper();
	}

	@Test
	public void getTransactionDTOFromTransactionTest() {
		Transaction transaction = new Transaction();
		transaction.setId(1L);
		transaction.setAccount(new Account());
		TransactionDTO transactionDTO = transactionMapper.getTransactionDTOFromTransaction(transaction);
		assertEquals(transaction.getId(), transactionDTO.getId());
	}

	@Test
	public void getTransactionFromTransactionDTOTest() {
		TransactionDTO transactionDTO = new TransactionDTO();
		Account account = new Account();
		transactionDTO.setId(1L);
		Transaction transaction = transactionMapper.getTransactionFromTransactionDTO(transactionDTO,account);
		assertEquals(transactionDTO.getId(), transaction.getId());
	}
}
