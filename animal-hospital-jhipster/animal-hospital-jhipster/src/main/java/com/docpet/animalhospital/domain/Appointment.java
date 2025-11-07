package com.docpet.animalhospital.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Appointment.
 */
@Entity
@Table(name = "appointment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "time_start", nullable = false)
    private ZonedDateTime timeStart;

    @NotNull
    @Column(name = "time_end", nullable = false)
    private ZonedDateTime timeEnd;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "notes")
    private String notes;

    @NotNull
    @Column(name = "appointment_type", nullable = false)
    private String appointmentType;

    @NotNull
    @Column(name = "location_type", nullable = false)
    private String locationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vet_id")
    private Vet vet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Appointment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTimeStart() {
        return this.timeStart;
    }

    public Appointment timeStart(ZonedDateTime timeStart) {
        this.setTimeStart(timeStart);
        return this;
    }

    public void setTimeStart(ZonedDateTime timeStart) {
        this.timeStart = timeStart;
    }

    public ZonedDateTime getTimeEnd() {
        return this.timeEnd;
    }

    public Appointment timeEnd(ZonedDateTime timeEnd) {
        this.setTimeEnd(timeEnd);
        return this;
    }

    public void setTimeEnd(ZonedDateTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getType() {
        return this.type;
    }

    public Appointment type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return this.status;
    }

    public Appointment status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return this.notes;
    }

    public Appointment notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAppointmentType() {
        return this.appointmentType;
    }

    public Appointment appointmentType(String appointmentType) {
        this.setAppointmentType(appointmentType);
        return this;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getLocationType() {
        return this.locationType;
    }

    public Appointment locationType(String locationType) {
        this.setLocationType(locationType);
        return this;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Pet getPet() {
        return this.pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Appointment pet(Pet pet) {
        this.setPet(pet);
        return this;
    }

    public Vet getVet() {
        return this.vet;
    }

    public void setVet(Vet vet) {
        this.vet = vet;
    }

    public Appointment vet(Vet vet) {
        this.setVet(vet);
        return this;
    }

    public Owner getOwner() {
        return this.owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Appointment owner(Owner owner) {
        this.setOwner(owner);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Appointment)) {
            return false;
        }
        return getId() != null && getId().equals(((Appointment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Appointment{" +
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
