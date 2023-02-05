package MappersTest;

import org.example.data.AccountDTO;
import org.example.entities.Account;
import org.example.entities.Client;
import org.example.mappers.AccountMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class AccountMapperTest {
	@Mock
	private AccountMapper accountMapper;

	@BeforeEach
	public void setUp() {
		accountMapper = new AccountMapper(); 
	}

	@Test
	public void getAccountDTOFromAccountTest() {
		Account account = new Account(2L,200,new Client(),null);
		AccountDTO accountDTO = accountMapper.getAccountDTOFromAccount(account);
		assertEquals(account.getRib(), accountDTO.getRib());      
	}

	@Test
	public void getAccountFromAccountDTOTest() {
		AccountDTO accountDTO = new AccountDTO();
		Client client = new Client();
		accountDTO.setRib(2L);
		Account account = accountMapper.getAccountFromAccountDTO(accountDTO,client);
		assertEquals(account.getRib(), accountDTO.getRib());

	}
}


