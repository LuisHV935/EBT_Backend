package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.ClientRequest;
import com.example.proyectofinal.entity.Client;
import com.example.proyectofinal.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
    }

    public Client create(ClientRequest request) {
        Client client = new Client(request.getNombre(), request.getTelefono(), request.getEmail(), request.getDireccion());
        return clientRepository.save(client);
    }

    public Client update(Long id, ClientRequest request) {
        Client client = findById(id);
        client.setNombre(request.getNombre());
        client.setTelefono(request.getTelefono());
        client.setEmail(request.getEmail());
        client.setDireccion(request.getDireccion());
        return clientRepository.save(client);
    }

    public void delete(Long id) {
        Client client = findById(id);
        clientRepository.delete(client);
    }
}
