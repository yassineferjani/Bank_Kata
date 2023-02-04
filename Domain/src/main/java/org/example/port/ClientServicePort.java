package org.example.port;

import org.example.data.ClientDTO;

import java.util.List;

public interface ClientServicePort {

    void add(ClientDTO clientDTO);
    List<ClientDTO> getAll();
    ClientDTO getById(Long id);
    void deleteById(Long id);
    void update(ClientDTO clientDTO);

}
