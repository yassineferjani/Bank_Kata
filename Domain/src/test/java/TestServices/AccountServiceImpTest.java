package TestServices;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.example.data.AccountDTO;
import org.example.data.ClientDTO;
import org.example.exception.ClientNotFoundException;
import org.example.port.*;
import org.example.service.AccountServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class AccountServiceImpTest {
    @Mock
    private AccountPersistencePort accountPersistencePort;

    @Mock
    private ClientServicePort clientServicePort;

    @InjectMocks
    private AccountServiceImp accountServiceImp;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void addValidAccount() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1L);
        AccountDTO account = new AccountDTO();
        account.setRib(1L);
        account.setBalance(100);
        account.setClientId(1L);

        when(clientServicePort.getById(clientDTO.getId())).thenReturn(clientDTO);
        accountServiceImp.add(account);

        verify(clientServicePort).getById(clientDTO.getId());
        verify(accountPersistencePort).add(account);
    }

    @Test
    public void addAccountNullClientThrowsException() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setClientId(1L);

        ClientNotFoundException exception = assertThrows(
                ClientNotFoundException.class,
                () -> accountServiceImp.add(accountDTO)
        );
        assertEquals("Client not found. An account must be associated with a valid client.", exception.getMessage());
    }

    @Test
    public void testGetAll() {
        List<AccountDTO> expectedAccounts = Arrays.asList(
        		new AccountDTO(1L, 2.0, 1L),
        		new AccountDTO(2L, 2.0, 1L)
        );
        when(accountPersistencePort.getAll()).thenReturn(expectedAccounts);
        List<AccountDTO> actualAccounts = accountServiceImp.getAll();
        assertEquals(expectedAccounts, actualAccounts);
    }

    @Test
    public void testGetById() {
        AccountDTO expectedAccount = new AccountDTO(1L, 2.0, 1L);
        when(accountPersistencePort.getById(1L)).thenReturn(expectedAccount);
        AccountDTO actualAccount = accountServiceImp.getById(1L);
        assertEquals(expectedAccount, actualAccount);
    }

    @Test
    public void testDeleteById() {
        Long id = 1L;
        AccountDTO accountDTO = new AccountDTO();
        when(accountPersistencePort.getById(id)).thenReturn(accountDTO);
        accountServiceImp.deleteById(id);
        verify(accountPersistencePort).getById(id);
        verify(accountPersistencePort).deleteById(id);
    }

    @Test
    public void testUpdate() {
        AccountDTO accountDTO = new AccountDTO(1L, 2.0, 1L);
        accountServiceImp.update(accountDTO);
        Mockito.verify(accountPersistencePort).update(accountDTO);
    }
}
