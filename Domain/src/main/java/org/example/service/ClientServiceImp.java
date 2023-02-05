package org.example.service;

import org.example.data.ClientDTO;
import org.example.exception.ClientNotFoundException;
import org.example.port.ClientPersistencePort;
import org.example.port.ClientServicePort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClientServiceImp implements ClientServicePort {
    private ClientPersistencePort clientPersistencePort;

    public ClientServiceImp (ClientPersistencePort clientPersistencePort){
        this.clientPersistencePort=clientPersistencePort;
    }
    @Override
    public void add(ClientDTO clientDTO) {
        clientPersistencePort.add(clientDTO);
    }

    @Override
    public List<ClientDTO> getAll() {
        return clientPersistencePort.getAll();
    }

    @Override
    public ClientDTO getById(Long id) {
        ClientDTO clientDTO = clientPersistencePort.getById(id);
        if (clientDTO == null)
                throw new ClientNotFoundException("Client Not Found");
        return clientDTO;
    }

    @Override
    public void deleteById(Long id) {
       clientPersistencePort.deleteById(id);
    }

    @Override
    public void update(ClientDTO clientDTO) {
        clientPersistencePort.update(clientDTO);
    }
}
