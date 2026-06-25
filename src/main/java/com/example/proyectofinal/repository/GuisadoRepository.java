package com.example.proyectofinal.repository;

import com.example.proyectofinal.entity.Guisado;
import com.example.proyectofinal.enums.GuisadoCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuisadoRepository extends JpaRepository<Guisado, Long> {
    List<Guisado> findByCategoria(GuisadoCategory categoria);
}
