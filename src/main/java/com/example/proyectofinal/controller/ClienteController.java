package com.example.proyectofinal.controller;

import com.example.proyectofinal.entity.Solicitud;
import com.example.proyectofinal.service.SolicitudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@Tag(name = "Cliente", description = "Endpoints para clientes autenticados")
public class ClienteController {

    private final SolicitudService solicitudService;

    public ClienteController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @GetMapping("/solicitudes")
    @Operation(summary = "Ver mis solicitudes de cotización")
    public ResponseEntity<List<Solicitud>> misSolicitudes(Authentication auth) {
        return ResponseEntity.ok(solicitudService.findByEmail(auth.getName()));
    }
}
