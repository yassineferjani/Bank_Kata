package MappersTest;

import static org.junit.Assert.assertEquals;

import org.example.data.AccountDTO;
import org.example.entities.Account;
import org.example.mappers.AccountMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


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
		Account account = new Account(2L,200,null,null);
		AccountDTO accountDTO = accountMapper.getAccountDTOFromAccount(account);
		assertEquals(account.getRib(), accountDTO.getRib());      
	}

	@Test
	public void getAccountFromAccountDTOTest() {
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setRib(2L);
		Account account = accountMapper.getAccountFromAccountDTO(accountDTO);
		assertEquals(account.getRib(), accountDTO.getRib());

	}
}


