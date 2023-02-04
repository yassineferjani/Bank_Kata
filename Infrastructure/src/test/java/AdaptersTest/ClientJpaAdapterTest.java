package AdaptersTest;

import org.example.adapters.ClientJpaAdapter;
import org.example.data.ClientDTO;
import org.example.entities.Client;
import org.example.mappers.ClientMapper;
import org.example.repositories.ClientDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

    @ExtendWith(MockitoExtension.class)
    public class ClientJpaAdapterTest {

        @Mock
        private ClientDAO clientDAO;

        @Mock
        private ClientMapper clientMapper;

        @InjectMocks
        private ClientJpaAdapter clientJpaAdapter;

        @Test
        public void testAddClient() {
            // setup
            ClientDTO clientDTO = new ClientDTO();
            Client client = new Client();
            when(clientMapper.getClientFromClientDTO(clientDTO)).thenReturn(client);
                    // execution
            clientJpaAdapter.addClient(clientDTO);
            // assertion
            verify(clientDAO, times(1)).save(client);
        }

        @Test
        public void testGetAllClients() {
            // setup
            Client client1 = new Client();
            Client client2 = new Client();
            List<Client> clients = Arrays.asList(client1, client2);
            ClientDTO clientDTO1 = new ClientDTO();
            ClientDTO clientDTO2 = new ClientDTO();
            when(clientDAO.findAll()).thenReturn(clients);
            when(clientMapper.getClientDTOFromClient(client1)).thenReturn(clientDTO1);
            when(clientMapper.getClientDTOFromClient(client2)).thenReturn(clientDTO2);
            // execution
            List<ClientDTO> result = clientJpaAdapter.getAllClients();
            // assertion
            assertEquals(2, result.size());
            assertEquals(clientDTO1, result.get(0));
            assertEquals(clientDTO2, result.get(1));
        }

        @Test
        public void testGetClientById() {
            // setup
            Long id = 1L;
            Client client = new Client();
            ClientDTO clientDTO = new ClientDTO();
            Optional<Client> optionalClient = Optional.of(client);
            when(clientDAO.findById(id)).thenReturn(optionalClient);
            when(clientMapper.getClientDTOFromClient(client)).thenReturn(clientDTO);
            // execution
            ClientDTO result = clientJpaAdapter.getClientById(id);
            // assertion
            assertEquals(clientDTO, result);
        }

        @Test
        public void testGetClientById_NotFound() {
            // setup
            Long id = 1L;
            Optional<Client> optionalClient = Optional.empty();
            when(clientDAO.findById(id)).thenReturn(optionalClient);
            // execution
            ClientDTO result = clientJpaAdapter.getClientById(id);
            // assertion
            assertNull(result);
        }

        @Test
        public void testDeleteClientById() {
            // setup
            Long id = 1L;
            // execution
            clientJpaAdapter.deleteClientById(id);
            // assertion
            verify(clientDAO, times(1)).deleteById(id);
        }

        @Test
        public void testUpdateTransaction() {
            testAddClient();
        }
    }
