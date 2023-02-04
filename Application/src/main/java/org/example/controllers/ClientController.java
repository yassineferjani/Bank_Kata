package org.example.controllers;

import org.example.data.ClientDTO;
import org.example.port.ClientServicePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private ClientServicePort clientServicePort;

    public ClientController(ClientServicePort clientServicePort){
        this.clientServicePort = clientServicePort;
    }

    @PostMapping("/add")
    public ResponseEntity<ClientDTO> add(@RequestBody ClientDTO clientDTO){
        clientServicePort.add(clientDTO);
        return new ResponseEntity<>(clientDTO, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<ClientDTO>> getAll(){
        return new ResponseEntity<>(clientServicePort.getAll(),HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<ClientDTO> getById(@RequestParam("id") Long id){
        return new ResponseEntity<>(clientServicePort.getById(id),HttpStatus.OK);
    }
    
    @DeleteMapping("/del/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
    	clientServicePort.deleteById(id);
       return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    
    @PutMapping("/update")
    public ResponseEntity<ClientDTO> update(@RequestBody ClientDTO clientDTO) {
    	clientServicePort.update(clientDTO);
        return new ResponseEntity<>(clientDTO,HttpStatus.OK);
    }

    
    
}
