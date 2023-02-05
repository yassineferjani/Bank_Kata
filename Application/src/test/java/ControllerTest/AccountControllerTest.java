package ControllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.controllers.AccountController;
import org.example.data.AccountDTO;
import org.example.port.AccountServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @Mock
    private AccountServicePort accountServicePort;

    @InjectMocks
    private AccountController accountController;

    private List<AccountDTO> accountDTOList;
    private AccountDTO accountDTO;

    @BeforeEach
    void setUp() {
        accountDTOList = new ArrayList<>();
        accountDTO = new AccountDTO();
        accountDTO.setRib(1L);
        accountDTO.setClientId(1L);
        accountDTO.setBalance(1000.0);
        accountDTOList.add(accountDTO);
    }

    @Test
    void testGetAll() {
        when(accountServicePort.getAll()).thenReturn(accountDTOList);
        ResponseEntity<List<AccountDTO>> response = accountController.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountDTOList, response.getBody());
    }

    @Test
    void testGetByRib() {
        when(accountServicePort.getById(1L)).thenReturn(Optional.of(accountDTO));
        ResponseEntity<AccountDTO> result = accountController.getByRib(1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(accountDTO, result.getBody());
    }


    @Test
    void testAdd() {
        accountController.add(accountDTO);
        verify(accountServicePort, times(1)).add(accountDTO);
    }

    @Test
    void testUpdate() {
        accountController.update(accountDTO);
        verify(accountServicePort, times(1)).update(accountDTO);
    }

    @Test
    void testDeleteById() {
        accountController.deleteById(1L);
        verify(accountServicePort, times(1)).deleteById(1L);
    }
}
