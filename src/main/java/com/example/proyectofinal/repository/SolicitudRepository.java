package com.example.proyectofinal.repository;

import com.example.proyectofinal.entity.Solicitud;
import com.example.proyectofinal.enums.SolicitudStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    List<Solicitud> findByStatus(SolicitudStatus status);
    List<Solicitud> findByEmail(String email);
}
