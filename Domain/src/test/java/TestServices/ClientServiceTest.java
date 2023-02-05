package TestServices;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.example.data.ClientDTO;
import org.example.port.ClientPersistencePort;
import org.example.service.ClientServiceImp;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ClientServiceTest {
	 private ClientPersistencePort clientPersistencePort = Mockito.mock(ClientPersistencePort.class);
	    private ClientServiceImp clientServiceImp = new ClientServiceImp(clientPersistencePort);

	    @Test
	    public void testAdd() {
	        ClientDTO clientDTO = new ClientDTO(1L,"test","test","1",null);
	        clientServiceImp.add(clientDTO);
	        verify(clientPersistencePort).add(clientDTO);
	    }

	    @Test
	    public void testGetAll() {
	        List<ClientDTO> expectedClients = Arrays.asList(
	        		new ClientDTO(1L,"test","test","1",null),
	        		new ClientDTO(2L,"test","test","1",null)
	        );
	        when(clientPersistencePort.getAll()).thenReturn(expectedClients);
	        List<ClientDTO> actualClients = clientServiceImp.getAll();
	        assertEquals(expectedClients, actualClients);
	    }

	    @Test
	    public void testGetById() {
	        ClientDTO expectedClient = new ClientDTO(2L,"test","test","1",null);
	        when(clientPersistencePort.getById(2L)).thenReturn(expectedClient);
	        ClientDTO actualClient = clientServiceImp.getById(2L);
	        assertEquals(expectedClient, actualClient);
	    }

	@Test
	void deleteById() {
		clientServiceImp.deleteById(1L);
		verify(clientPersistencePort).deleteById(1L);
	}

	    @Test
	    public void testUpdate() {
	        ClientDTO clientDTO = new ClientDTO(1L,"test","test","1",null);
	        clientServiceImp.update(clientDTO);
	        verify(clientPersistencePort).update(clientDTO);
	    }
	}