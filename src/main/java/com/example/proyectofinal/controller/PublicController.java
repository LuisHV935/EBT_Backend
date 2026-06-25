package com.example.proyectofinal.controller;

import com.example.proyectofinal.dto.SolicitudRequest;
import com.example.proyectofinal.entity.Guisado;
import com.example.proyectofinal.entity.MenuItem;
import com.example.proyectofinal.entity.Salsa;
import com.example.proyectofinal.entity.Solicitud;
import com.example.proyectofinal.service.CatalogoService;
import com.example.proyectofinal.service.GuisadoService;
import com.example.proyectofinal.service.MenuItemService;
import com.example.proyectofinal.service.SalsaService;
import com.example.proyectofinal.service.SolicitudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
@Tag(name = "Público", description = "Endpoints sin autenticación")
public class PublicController {

    private final CatalogoService catalogoService;
    private final SolicitudService solicitudService;
    private final GuisadoService guisadoService;
    private final MenuItemService menuItemService;
    private final SalsaService salsaService;

    public PublicController(CatalogoService catalogoService,
                            SolicitudService solicitudService,
                            GuisadoService guisadoService,
                            MenuItemService menuItemService,
                            SalsaService salsaService) {
        this.catalogoService = catalogoService;
        this.solicitudService = solicitudService;
        this.guisadoService = guisadoService;
        this.menuItemService = menuItemService;
        this.salsaService = salsaService;
    }

    @GetMapping("/catalogo")
    @Operation(summary = "Obtener catálogo completo del menú")
    public ResponseEntity<Map<String, Object>> getCatalogo() {
        return ResponseEntity.ok(catalogoService.getCatalogo());
    }

    @GetMapping("/guisados")
    @Operation(summary = "Listar todos los guisados para taquiza")
    public ResponseEntity<List<Guisado>> getGuisados() {
        return ResponseEntity.ok(guisadoService.findAll());
    }

    @GetMapping("/menu-items")
    @Operation(summary = "Listar todos los items del menú de 3 tiempos / plato volado")
    public ResponseEntity<List<MenuItem>> getMenuItems() {
        return ResponseEntity.ok(menuItemService.findAll());
    }

    @GetMapping("/salsas")
    @Operation(summary = "Listar todas las salsas disponibles")
    public ResponseEntity<List<Salsa>> getSalsas() {
        return ResponseEntity.ok(salsaService.findAll());
    }

    @PostMapping("/solicitudes")
    @Operation(summary = "Enviar una solicitud de cotización")
    public ResponseEntity<Solicitud> createSolicitud(@Valid @RequestBody SolicitudRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(solicitudService.create(request));
    }
}
