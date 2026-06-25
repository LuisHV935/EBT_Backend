package com.example.proyectofinal.service;

import com.example.proyectofinal.entity.Salsa;
import com.example.proyectofinal.repository.SalsaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalsaService {

    private final SalsaRepository salsaRepository;

    public SalsaService(SalsaRepository salsaRepository) {
        this.salsaRepository = salsaRepository;
    }

    public List<Salsa> findAll() {
        return salsaRepository.findAll();
    }

    public List<Salsa> findByTipo(String tipo) {
        return salsaRepository.findByTipo(tipo);
    }

    public Salsa findById(Long id) {
        return salsaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salsa no encontrada con id: " + id));
    }

    public Salsa create(Salsa salsa) {
        return salsaRepository.save(salsa);
    }

    public Salsa update(Long id, Salsa salsa) {
        Salsa existing = findById(id);
        existing.setNombre(salsa.getNombre());
        existing.setTipo(salsa.getTipo());
        existing.setDescripcion(salsa.getDescripcion());
        return salsaRepository.save(existing);
    }

    public void delete(Long id) {
        Salsa salsa = findById(id);
        salsaRepository.delete(salsa);
    }
}
