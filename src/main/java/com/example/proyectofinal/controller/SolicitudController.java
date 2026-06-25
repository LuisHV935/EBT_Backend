package com.example.proyectofinal.controller;

import com.example.proyectofinal.entity.Solicitud;
import com.example.proyectofinal.enums.SolicitudStatus;
import com.example.proyectofinal.service.SolicitudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/solicitudes")
@Tag(name = "Solicitudes", description = "Gestión de solicitudes de cotización")
public class SolicitudController {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las solicitudes recibidas")
    public ResponseEntity<List<Solicitud>> findAll() {
        return ResponseEntity.ok(solicitudService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ver detalle de una solicitud")
    public ResponseEntity<Solicitud> findById(@PathVariable Long id) {
        return ResponseEntity.ok(solicitudService.findById(id));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Actualizar estado de una solicitud")
    public ResponseEntity<Solicitud> updateStatus(@PathVariable Long id, @RequestBody SolicitudStatus status) {
        return ResponseEntity.ok(solicitudService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una solicitud")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        solicitudService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
