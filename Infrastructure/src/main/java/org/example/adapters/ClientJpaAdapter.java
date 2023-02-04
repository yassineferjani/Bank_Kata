package org.example.adapters;

import org.example.data.ClientDTO;
import org.example.entities.Client;
import org.example.mappers.ClientMapper;
import org.example.port.ClientPersistencePort;
import org.example.repositories.ClientDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@Transactional
public class ClientJpaAdapter implements ClientPersistencePort {
    @Autowired
    private ClientDAO clientDAO;


    @Override
    public void add(ClientDTO clientDTO) {
        Client client = ClientMapper.getClientFromClientDTO(clientDTO);
        clientDAO.save(client);
    }

    @Override
    public List<ClientDTO> getAll() {
        List<Client> clients = clientDAO.findAll();	
        return clients.stream().map(c->ClientMapper.getClientDTOFromClient(c)).collect(Collectors.toList());
    }

    @Override
    public ClientDTO getById(Long id) {
        Optional<Client> client = clientDAO.findById(id);
        if (client.isPresent())
            return ClientMapper.getClientDTOFromClient(client.get());
        else
            return null;
    }

    @Override
    public void deleteById(Long id) {
        clientDAO.deleteById(id);
    }

    @Override
    public void update(ClientDTO clientDTO) {
        add(clientDTO);
    }
}
