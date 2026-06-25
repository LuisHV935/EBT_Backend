package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.SolicitudRequest;
import com.example.proyectofinal.entity.*;
import com.example.proyectofinal.enums.ServiceType;
import com.example.proyectofinal.enums.SolicitudStatus;
import com.example.proyectofinal.repository.SolicitudRepository;
import com.example.proyectofinal.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final GuisadoService guisadoService;
    private final MenuItemService menuItemService;
    private final SalsaService salsaService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SolicitudService(SolicitudRepository solicitudRepository,
                            GuisadoService guisadoService,
                            MenuItemService menuItemService,
                            SalsaService salsaService,
                            UserRepository userRepository,
                            PasswordEncoder passwordEncoder) {
        this.solicitudRepository = solicitudRepository;
        this.guisadoService = guisadoService;
        this.menuItemService = menuItemService;
        this.salsaService = salsaService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Solicitud> findAll() {
        return solicitudRepository.findAll();
    }

    public Solicitud findById(Long id) {
        return solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con id: " + id));
    }

    @Transactional
    public Solicitud create(SolicitudRequest request) {
        Solicitud s = new Solicitud();
        s.setNombreContacto(request.getNombreContacto());
        s.setTelefono(request.getTelefono());
        s.setEmail(request.getEmail());
        s.setDireccion(request.getDireccion());
        s.setServiceType(ServiceType.valueOf(request.getServiceType()));
        s.setEventDate(request.getEventDate());
        s.setNumberOfPeople(request.getNumberOfPeople());
        s.setIncludesTableware(request.getIncludesTableware() != null && request.getIncludesTableware());
        s.setAguaFrescaVariety(request.getAguaFrescaVariety());
        s.setComentarios(request.getComentarios());

        if (ServiceType.TAQUIZA.equals(s.getServiceType()) && request.getSelectedGuisadoIds() != null) {
            s.setSelectedGuisados(request.getSelectedGuisadoIds().stream()
                    .map(guisadoService::findById).toList());
        } else {
            if (request.getSelectedCremaId() != null)
                s.setSelectedCrema(menuItemService.findById(request.getSelectedCremaId()));
            if (request.getSelectedPastaId() != null)
                s.setSelectedPasta(menuItemService.findById(request.getSelectedPastaId()));
            if (request.getSelectedPlatoFuerteId() != null)
                s.setSelectedPlatoFuerte(menuItemService.findById(request.getSelectedPlatoFuerteId()));
            if (request.getSelectedSalsaId() != null)
                s.setSelectedSalsa(salsaService.findById(request.getSelectedSalsaId()));
            if (request.getSelectedEnsaladaId() != null)
                s.setSelectedEnsalada(menuItemService.findById(request.getSelectedEnsaladaId()));
            if (request.getSelectedGuarnicionId() != null)
                s.setSelectedGuarnicion(menuItemService.findById(request.getSelectedGuarnicionId()));
        }

        Solicitud saved = solicitudRepository.save(s);

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            userRepository.findByUsername(request.getEmail()).orElseGet(() -> {
                User user = new User(request.getEmail(), passwordEncoder.encode(request.getPassword()), "CLIENTE");
                return userRepository.save(user);
            });
        }

        return saved;
    }

    @Transactional
    public Solicitud updateStatus(Long id, SolicitudStatus status) {
        Solicitud s = findById(id);
        s.setStatus(status);
        return solicitudRepository.save(s);
    }

    public List<Solicitud> findByEmail(String email) {
        return solicitudRepository.findByEmail(email);
    }

    public void delete(Long id) {
        solicitudRepository.delete(findById(id));
    }
}
