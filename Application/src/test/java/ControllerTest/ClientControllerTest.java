package ControllerTest;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.example.controllers.ClientController;
import org.example.data.AccountDTO;
import org.example.data.ClientDTO;
import org.example.port.ClientServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Mock
    private ClientServicePort clientServicePort;

    @InjectMocks
    private ClientController clientController;

    private ClientDTO clientDTO;
    private List<ClientDTO> clientDTOList;

    @BeforeEach
    void setUp() {
        clientDTO = new ClientDTO();
        clientDTO.setId(1L);
        clientDTO.setName("John Doe");
        clientDTOList = Arrays.asList(clientDTO);
    }

    @Test
    void testAdd() {
        clientController.add(clientDTO);
        verify(clientServicePort, times(1)).add(clientDTO);
    }

    @Test
    void testGetAll() {
        when(clientServicePort.getAll()).thenReturn(clientDTOList);
        ResponseEntity<List<ClientDTO>> response = clientController.getAll();
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(clientDTOList, response.getBody());
    }

    @Test
    void testGetById() {
        when(clientServicePort.getById(clientDTO.getId())).thenReturn(clientDTO);
        ResponseEntity<ClientDTO> response = clientController.getById(1L);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(clientDTO, response.getBody());
    }

    @Test
    void testDelete() {
        clientController.delete(clientDTO.getId());
        verify(clientServicePort, times(1)).deleteById(clientDTO.getId());
    }

    @Test
    void testUpdate() {
        clientController.update(clientDTO);
        verify(clientServicePort, times(1)).update(clientDTO);
    }
}
