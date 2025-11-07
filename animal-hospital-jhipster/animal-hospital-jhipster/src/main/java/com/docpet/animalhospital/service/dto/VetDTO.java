package com.docpet.animalhospital.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.docpet.animalhospital.domain.Vet} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VetDTO implements Serializable {

    private Long id;

    @NotNull
    private String licenseNo;

    private String specialization;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VetDTO)) {
            return false;
        }

        VetDTO vetDTO = (VetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VetDTO{" +
            "id=" + getId() +
            ", licenseNo='" + getLicenseNo() + "'" +
            ", specialization='" + getSpecialization() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
