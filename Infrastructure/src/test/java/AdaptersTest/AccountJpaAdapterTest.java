package AdaptersTest;

import org.example.adapters.AccountJpaAdapter;
import org.example.data.AccountDTO;
import org.example.data.ClientDTO;
import org.example.entities.Account;
import org.example.entities.Client;
import org.example.mappers.AccountMapper;
import org.example.repositories.AccountDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountJpaAdapterTest {
    @Mock
    private AccountDAO accountDAO;
    @Mock
    private AccountMapper accountMapper;
    @InjectMocks
    private AccountJpaAdapter accountJpaAdapter;

    @Test
    public void testAddAccount() {
        //setup
        AccountDTO accountDTO = new AccountDTO();
        Account account = new Account();
        when(accountMapper.getAccountFromAccountDTO(accountDTO)).thenReturn(account);
        accountJpaAdapter.addAccount(accountDTO);
        verify(accountDAO,times(1)).save(account);
    }

    @Test
    public void testGetAllAccounts(){
        Account account1 = new Account();
        Account account2 = new Account();
        List<Account> accounts = Arrays.asList(account1,account2);
        AccountDTO accountDTO1 = new AccountDTO();
        AccountDTO accountDTO2 = new AccountDTO();
        when(accountDAO.findAll()).thenReturn(accounts);
        when(accountMapper.getAccountDTOFromAccount(account1)).thenReturn(accountDTO1);
        when(accountMapper.getAccountDTOFromAccount(account2)).thenReturn(accountDTO2);
        List<AccountDTO> result = accountJpaAdapter.getAllAccounts();
        assertEquals(2,result.size());
        assertEquals(accountDTO1,result.get(0));
        assertEquals(accountDTO2,result.get(1));
    }

    @Test
    public void testGetAccountById(){
        Long id = 1L;
        Account account = new Account();
        AccountDTO accountDTO = new AccountDTO();
        Optional<Account> optionalAccount = Optional.of(account);
        when(accountDAO.findById(id)).thenReturn(optionalAccount);
        when(accountMapper.getAccountDTOFromAccount(account)).thenReturn(accountDTO);
        // execution
        AccountDTO result = accountJpaAdapter.getAccountById(id);
        // assertion
        assertEquals(accountDTO, result);
    }
    @Test
    public void testGetAccountById_NotFound() {
        // setup
        Long id = 1L;
        Optional<Account> optionalClient = Optional.empty();
        when(accountDAO.findById(id)).thenReturn(optionalClient);
        // execution
        AccountDTO result = accountJpaAdapter.getAccountById(id);
        // assertion
        assertNull(result);
    }

    @Test
    public void testDeleteAccountById() {
        // setup
        Long id = 1L;
        // execution
        accountJpaAdapter.deleteAccountById(id);
        // assertion
        verify(accountDAO, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateAccount() {
        testAddAccount();
    }
}
