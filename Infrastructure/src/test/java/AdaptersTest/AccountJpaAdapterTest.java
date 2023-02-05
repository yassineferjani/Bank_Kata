package AdaptersTest;

import org.example.adapters.AccountJpaAdapter;
import org.example.data.AccountDTO;
import org.example.data.ClientDTO;
import org.example.entities.Account;
import org.example.entities.Client;
import org.example.mappers.AccountMapper;
import org.example.port.ClientPersistencePort;
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
    @Mock
    private ClientPersistencePort clientPersistencePort;
    @InjectMocks
    private AccountJpaAdapter accountJpaAdapter;

    @Test
    public void testAddAccount() {
        AccountDTO accountDTO = new AccountDTO();
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1L);
        accountDTO.setClientId(clientDTO.getId());
        when(clientPersistencePort.getById(anyLong())).thenReturn(clientDTO);
        accountJpaAdapter.add(accountDTO);
        verify(accountDAO, times(1)).save(any(Account.class));
    }

    @Test
    public void testGetAllAccounts(){
        List<Account> accounts = List.of(new Account(1L,2000.0,new Client(),null), new Account(2L,2000.0,new Client(),null));
        when(accountDAO.findAll()).thenReturn(accounts);

        List<AccountDTO> result = accountJpaAdapter.getAll();

        verify(accountDAO, times(1)).findAll();
        assertEquals(accounts.size(), result.size());
    }

    @Test
    public void testGetAccountById(){
        Long id = 1L;
        Optional<Account> account = Optional.of(new Account(1L,2000.0,new Client(),null));

        when(accountDAO.findById(id)).thenReturn(account);

        AccountDTO result = accountJpaAdapter.getById(id);

        verify(accountDAO, times(1)).findById(id);
        assertNotNull(result);
    }
    @Test
    public void testGetAccountById_NotFound() {
        Long id = 1L;
        Optional<Account> account = Optional.empty();

        when(accountDAO.findById(id)).thenReturn(account);

        AccountDTO result = accountJpaAdapter.getById(id);

        verify(accountDAO, times(1)).findById(id);
        assertNull(result);
    }

    @Test
    public void testDeleteAccountById() {
        // setup
        Long id = 1L;
        // execution
        accountJpaAdapter.deleteById(id);
        // assertion
        verify(accountDAO, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateAccount() {
        testAddAccount();
    }
}
