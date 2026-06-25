package com.example.proyectofinal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.List;

public class SolicitudRequest {

    @NotBlank
    private String nombreContacto;

    private String telefono;

    @NotBlank @Email
    private String email;

    private String direccion;

    @NotBlank
    private String serviceType;

    private LocalDate eventDate;

    @NotNull @Positive
    private Integer numberOfPeople;

    private List<Long> selectedGuisadoIds;

    private Long selectedCremaId;
    private Long selectedPastaId;
    private Long selectedPlatoFuerteId;
    private Long selectedSalsaId;
    private Long selectedEnsaladaId;
    private Long selectedGuarnicionId;

    private Boolean includesTableware;
    private String aguaFrescaVariety;
    private String comentarios;
    private String password;

    public @NotBlank String getNombreContacto() { return nombreContacto; }
    public void setNombreContacto(@NotBlank String nombreContacto) { this.nombreContacto = nombreContacto; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public @NotBlank @Email String getEmail() { return email; }
    public void setEmail(@NotBlank @Email String email) { this.email = email; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public @NotBlank String getServiceType() { return serviceType; }
    public void setServiceType(@NotBlank String serviceType) { this.serviceType = serviceType; }
    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }
    public @NotNull @Positive Integer getNumberOfPeople() { return numberOfPeople; }
    public void setNumberOfPeople(@NotNull @Positive Integer numberOfPeople) { this.numberOfPeople = numberOfPeople; }
    public List<Long> getSelectedGuisadoIds() { return selectedGuisadoIds; }
    public void setSelectedGuisadoIds(List<Long> selectedGuisadoIds) { this.selectedGuisadoIds = selectedGuisadoIds; }
    public Long getSelectedCremaId() { return selectedCremaId; }
    public void setSelectedCremaId(Long selectedCremaId) { this.selectedCremaId = selectedCremaId; }
    public Long getSelectedPastaId() { return selectedPastaId; }
    public void setSelectedPastaId(Long selectedPastaId) { this.selectedPastaId = selectedPastaId; }
    public Long getSelectedPlatoFuerteId() { return selectedPlatoFuerteId; }
    public void setSelectedPlatoFuerteId(Long selectedPlatoFuerteId) { this.selectedPlatoFuerteId = selectedPlatoFuerteId; }
    public Long getSelectedSalsaId() { return selectedSalsaId; }
    public void setSelectedSalsaId(Long selectedSalsaId) { this.selectedSalsaId = selectedSalsaId; }
    public Long getSelectedEnsaladaId() { return selectedEnsaladaId; }
    public void setSelectedEnsaladaId(Long selectedEnsaladaId) { this.selectedEnsaladaId = selectedEnsaladaId; }
    public Long getSelectedGuarnicionId() { return selectedGuarnicionId; }
    public void setSelectedGuarnicionId(Long selectedGuarnicionId) { this.selectedGuarnicionId = selectedGuarnicionId; }
    public Boolean getIncludesTableware() { return includesTableware; }
    public void setIncludesTableware(Boolean includesTableware) { this.includesTableware = includesTableware; }
    public String getAguaFrescaVariety() { return aguaFrescaVariety; }
    public void setAguaFrescaVariety(String aguaFrescaVariety) { this.aguaFrescaVariety = aguaFrescaVariety; }
    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
