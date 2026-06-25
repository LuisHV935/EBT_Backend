package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.GuisadoRequest;
import com.example.proyectofinal.entity.Guisado;
import com.example.proyectofinal.enums.GuisadoCategory;
import com.example.proyectofinal.repository.GuisadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuisadoService {

    private final GuisadoRepository guisadoRepository;

    public GuisadoService(GuisadoRepository guisadoRepository) {
        this.guisadoRepository = guisadoRepository;
    }

    public List<Guisado> findAll() {
        return guisadoRepository.findAll();
    }

    public List<Guisado> findByCategoria(GuisadoCategory categoria) {
        return guisadoRepository.findByCategoria(categoria);
    }

    public Guisado findById(Long id) {
        return guisadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guisado no encontrado con id: " + id));
    }

    public Guisado create(GuisadoRequest request) {
        Guisado guisado = new Guisado(request.getNombre(), request.getCategoria());
        return guisadoRepository.save(guisado);
    }

    public Guisado update(Long id, GuisadoRequest request) {
        Guisado guisado = findById(id);
        guisado.setNombre(request.getNombre());
        guisado.setCategoria(request.getCategoria());
        return guisadoRepository.save(guisado);
    }

    public void delete(Long id) {
        Guisado guisado = findById(id);
        guisadoRepository.delete(guisado);
    }
}
