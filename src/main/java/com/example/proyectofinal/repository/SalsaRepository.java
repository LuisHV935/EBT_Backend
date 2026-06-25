package com.example.proyectofinal.repository;

import com.example.proyectofinal.entity.Salsa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalsaRepository extends JpaRepository<Salsa, Long> {
    List<Salsa> findByTipo(String tipo);
}
