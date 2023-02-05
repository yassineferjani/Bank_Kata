package MappersTest;

import org.example.data.ClientDTO;
import org.example.entities.Client;
import org.example.mappers.ClientMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientMapperTest {
	@Mock
	private ClientMapper clientMapper;

    @BeforeEach
    public void setUp() {
        clientMapper = new ClientMapper();
    }

    @Test
    public void getClientDTOFromClientTest() {
    	Client client  = new Client(1L, null, null,"0", null,null);
        ClientDTO clientDTO = clientMapper.getClientDTOFromClient(client);
        assertEquals(client.getId(), clientDTO.getId());
    }

    @Test
    public void getClientFromClientDTOTest() {
    	ClientDTO clientDTO = new ClientDTO();
    	clientDTO.setId(1L);
        Client client = clientMapper.getClientFromClientDTO(clientDTO);
        assertEquals(clientDTO.getId(), client.getId());
    }
}
