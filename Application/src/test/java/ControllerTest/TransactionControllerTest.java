package ControllerTest;

import org.example.controllers.TransactionController;
import org.example.data.AccountDTO;
import org.example.data.TransactionType;
import org.example.data.TransactionDTO;
import org.example.port.CreditCardServicePort;
import org.example.port.TransactionServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class TransactionControllerTest {
    @Mock
    private TransactionServicePort transactionServicePort;
    @Mock
    private CreditCardServicePort creditCardServicePort;

    @InjectMocks
    private TransactionController transactionController;

    private List<TransactionDTO> transactionDTOList;
    private TransactionDTO transactionDTO;

    @BeforeEach
    void setUp() {
        transactionDTOList = new ArrayList<>();
        transactionDTO = new TransactionDTO();
        transactionDTO.setId(1L);
        transactionDTO.setTransactionType(TransactionType.Withdrawal);
        transactionDTO.setAccountId(1L);
        transactionDTOList.add(transactionDTO);
    }

    @Test
    void testGetAll() {
        when(transactionServicePort.getAll()).thenReturn(transactionDTOList);
        ResponseEntity<List<TransactionDTO>> response = transactionController.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactionDTOList, response.getBody());
    }

    @Test
    void testGetByID() {
        when(transactionServicePort.getById(1L)).thenReturn(transactionDTO);
        ResponseEntity<TransactionDTO> response = transactionController.getById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactionDTO, response.getBody());
    }

    @Test
    void testGetByAccountID() {
        Long accountId= 1L;
        when(transactionServicePort.getByAccountId(1L)).thenReturn(transactionDTOList);
        ResponseEntity<List<TransactionDTO>> response = transactionController.getByAccountId(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactionDTOList, response.getBody());
    }

    @Test
    void testAdd() {
        transactionController.withdrawal(transactionDTO.getAccountId(),20.0);
        verify(creditCardServicePort, times(1)).withdrawal(transactionDTO.getAccountId(),20.0);
    }


    @Test
    void testDeleteById() {
        transactionController.deleteById(1L);
        verify(transactionServicePort, times(1)).deleteById(1L);
    }
}
