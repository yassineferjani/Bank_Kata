package org.example.mappers;

import org.example.data.ClientDTO;
import org.example.entities.Client;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


public class ClientMapper {
	public static ClientDTO getClientDTOFromClient (Client client) {
		return ClientDTO.builder()
				.cin(client.getCin())
				.id(client.getId())
				.email(client.getEmail())
				.birthDate(client.getBirthDate())
				.build();
	}
	
	public static Client getClientFromClientDTO (ClientDTO clientDTO) {
		return Client.builder()
				.id(clientDTO.getId())
				.email(clientDTO.getEmail())
				.cin(clientDTO.getCin())
				.birthDate(clientDTO.getBirthDate())
				.build();
		
	}
	
	
}
