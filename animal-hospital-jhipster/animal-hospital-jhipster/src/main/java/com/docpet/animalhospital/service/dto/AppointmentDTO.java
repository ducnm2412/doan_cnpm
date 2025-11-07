package com.docpet.animalhospital.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.docpet.animalhospital.domain.Appointment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppointmentDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime timeStart;

    @NotNull
    private ZonedDateTime timeEnd;

    @NotNull
    private String type;

    @NotNull
    private String status;

    private String notes;

    @NotNull
    private String appointmentType;

    @NotNull
    private String locationType;

    private PetDTO pet;

    private VetDTO vet;

    private OwnerDTO owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(ZonedDateTime timeStart) {
        this.timeStart = timeStart;
    }

    public ZonedDateTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(ZonedDateTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public PetDTO getPet() {
        return pet;
    }

    public void setPet(PetDTO pet) {
        this.pet = pet;
    }

    public VetDTO getVet() {
        return vet;
    }

    public void setVet(VetDTO vet) {
        this.vet = vet;
    }

    public OwnerDTO getOwner() {
        return owner;
    }

    public void setOwner(OwnerDTO owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppointmentDTO)) {
            return false;
        }

        AppointmentDTO appointmentDTO = (AppointmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appointmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppointmentDTO{" +
            "id=" + getId() +
            ", timeStart='" + getTimeStart() + "'" +
            ", timeEnd='" + getTimeEnd() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", notes='" + getNotes() + "'" +
            ", appointmentType='" + getAppointmentType() + "'" +
            ", locationType='" + getLocationType() + "'" +
            ", pet=" + (getPet() != null ? getPet().getId() : null) +
            ", vet=" + (getVet() != null ? getVet().getId() : null) +
            ", owner=" + (getOwner() != null ? getOwner().getId() : null) +
            "}";
    }
}
