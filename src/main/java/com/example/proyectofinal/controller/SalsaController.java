package com.example.proyectofinal.controller;

import com.example.proyectofinal.entity.Salsa;
import com.example.proyectofinal.service.SalsaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salsas")
@Tag(name = "Salsas", description = "CRUD de salsas disponibles")
public class SalsaController {

    private final SalsaService salsaService;

    public SalsaController(SalsaService salsaService) {
        this.salsaService = salsaService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las salsas")
    public ResponseEntity<List<Salsa>> findAll() {
        return ResponseEntity.ok(salsaService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener salsa por ID")
    public ResponseEntity<Salsa> findById(@PathVariable Long id) {
        return ResponseEntity.ok(salsaService.findById(id));
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Filtrar salsas por tipo (POLLO/CERDO)")
    public ResponseEntity<List<Salsa>> findByTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(salsaService.findByTipo(tipo));
    }

    @PostMapping
    @Operation(summary = "Crear una nueva salsa")
    public ResponseEntity<Salsa> create(@RequestBody Salsa salsa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(salsaService.create(salsa));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una salsa")
    public ResponseEntity<Salsa> update(@PathVariable Long id, @RequestBody Salsa salsa) {
        return ResponseEntity.ok(salsaService.update(id, salsa));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una salsa")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        salsaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
