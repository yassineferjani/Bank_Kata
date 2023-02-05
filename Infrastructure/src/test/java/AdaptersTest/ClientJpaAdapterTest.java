package AdaptersTest;

import org.example.adapters.ClientJpaAdapter;
import org.example.data.ClientDTO;
import org.example.entities.Client;
import org.example.exception.ClientNotFoundException;
import org.example.mappers.ClientMapper;
import org.example.repositories.ClientDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
        private ClientJpaAdapter clientJpaAdapter;
        private ClientDTO clientDTO;
        private Client client;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
            clientJpaAdapter = new ClientJpaAdapter(clientDAO);
            clientDTO = ClientDTO.builder().id(1L).email("test@email.com").cin("cin").birthDate(null).build();
            client = ClientMapper.getClientFromClientDTO(clientDTO);
        }

        @Test
        public void testAddClient() {
            // setup
            clientJpaAdapter.add(clientDTO);
            verify(clientDAO).save(client);
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
            // execution
            List<ClientDTO> result = clientJpaAdapter.getAll();
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
            // execution
            ClientDTO result = clientJpaAdapter.getById(id);
            // assertion
            assertEquals(clientDTO, result);
        }

        @Test
        public void testGetClientByIdIfNotFound() {
            when(clientDAO.findById(2L)).thenReturn(Optional.empty());
            assertThrows(ClientNotFoundException.class, () -> clientJpaAdapter.getById(2L));
        }


        @Test
        public void testDeleteClientById() {
            Long id = 1L;
            clientJpaAdapter.deleteById(id);
            verify(clientDAO, times(1)).deleteById(id);
        }

        @Test
        public void testUpdateTransaction() {
            testAddClient();
        }
    }
