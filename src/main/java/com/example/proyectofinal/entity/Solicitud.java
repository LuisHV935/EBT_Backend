package com.example.proyectofinal.entity;

import com.example.proyectofinal.enums.ServiceType;
import com.example.proyectofinal.enums.SolicitudStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreContacto;

    private String telefono;

    @Column(nullable = false)
    private String email;

    private String direccion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceType serviceType;

    private LocalDate eventDate;

    @Column(nullable = false)
    private Integer numberOfPeople;

    @ManyToMany
    @JoinTable(
        name = "solicitud_guisados",
        joinColumns = @JoinColumn(name = "solicitud_id"),
        inverseJoinColumns = @JoinColumn(name = "guisado_id")
    )
    private List<Guisado> selectedGuisados = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private MenuItem selectedCrema;

    @ManyToOne(fetch = FetchType.LAZY)
    private MenuItem selectedPasta;

    @ManyToOne(fetch = FetchType.LAZY)
    private MenuItem selectedPlatoFuerte;

    @ManyToOne(fetch = FetchType.LAZY)
    private Salsa selectedSalsa;

    @ManyToOne(fetch = FetchType.LAZY)
    private MenuItem selectedEnsalada;

    @ManyToOne(fetch = FetchType.LAZY)
    private MenuItem selectedGuarnicion;

    private Boolean includesTableware;

    private String aguaFrescaVariety;

    @Column(length = 2000)
    private String comentarios;

    @Enumerated(EnumType.STRING)
    private SolicitudStatus status;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = SolicitudStatus.NUEVA;
        }
    }

    public Solicitud() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombreContacto() { return nombreContacto; }
    public void setNombreContacto(String nombreContacto) { this.nombreContacto = nombreContacto; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public ServiceType getServiceType() { return serviceType; }
    public void setServiceType(ServiceType serviceType) { this.serviceType = serviceType; }
    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }
    public Integer getNumberOfPeople() { return numberOfPeople; }
    public void setNumberOfPeople(Integer numberOfPeople) { this.numberOfPeople = numberOfPeople; }
    public List<Guisado> getSelectedGuisados() { return selectedGuisados; }
    public void setSelectedGuisados(List<Guisado> selectedGuisados) { this.selectedGuisados = selectedGuisados; }
    public MenuItem getSelectedCrema() { return selectedCrema; }
    public void setSelectedCrema(MenuItem selectedCrema) { this.selectedCrema = selectedCrema; }
    public MenuItem getSelectedPasta() { return selectedPasta; }
    public void setSelectedPasta(MenuItem selectedPasta) { this.selectedPasta = selectedPasta; }
    public MenuItem getSelectedPlatoFuerte() { return selectedPlatoFuerte; }
    public void setSelectedPlatoFuerte(MenuItem selectedPlatoFuerte) { this.selectedPlatoFuerte = selectedPlatoFuerte; }
    public Salsa getSelectedSalsa() { return selectedSalsa; }
    public void setSelectedSalsa(Salsa selectedSalsa) { this.selectedSalsa = selectedSalsa; }
    public MenuItem getSelectedEnsalada() { return selectedEnsalada; }
    public void setSelectedEnsalada(MenuItem selectedEnsalada) { this.selectedEnsalada = selectedEnsalada; }
    public MenuItem getSelectedGuarnicion() { return selectedGuarnicion; }
    public void setSelectedGuarnicion(MenuItem selectedGuarnicion) { this.selectedGuarnicion = selectedGuarnicion; }
    public Boolean getIncludesTableware() { return includesTableware; }
    public void setIncludesTableware(Boolean includesTableware) { this.includesTableware = includesTableware; }
    public String getAguaFrescaVariety() { return aguaFrescaVariety; }
    public void setAguaFrescaVariety(String aguaFrescaVariety) { this.aguaFrescaVariety = aguaFrescaVariety; }
    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
    public SolicitudStatus getStatus() { return status; }
    public void setStatus(SolicitudStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
