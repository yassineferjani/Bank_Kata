package org.example.adapters;

import org.example.data.ClientDTO;
import org.example.entities.Client;
import org.example.exception.ClientNotFoundException;
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
    private ClientDAO clientDAO;
    public ClientJpaAdapter(ClientDAO clientDAO){
        this.clientDAO = clientDAO;
    }


    @Override
    public void add(ClientDTO clientDTO) {
        Client client = ClientMapper.getClientFromClientDTO(clientDTO);
        clientDAO.save(client);
    }

    @Override
    public List<ClientDTO> getAll() {
        return clientDAO.findAll().stream().map(c->ClientMapper.getClientDTOFromClient(c)).collect(Collectors.toList());
    }

    @Override
    public ClientDTO getById(Long id) {
       Optional<Client> client = clientDAO.findById(id);
       return ClientMapper.getClientDTOFromClient(client.get());
    }

    @Override
    public void deleteById(Long id) {
        clientDAO.deleteById(id);
    }

    @Override
    public void update(ClientDTO clientDTO) {
        Client client = ClientMapper.getClientFromClientDTO(clientDTO);
        clientDAO.save(client);
    }
}
