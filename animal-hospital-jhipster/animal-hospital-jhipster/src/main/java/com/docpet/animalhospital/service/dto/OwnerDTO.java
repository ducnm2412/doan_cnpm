package com.docpet.animalhospital.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.docpet.animalhospital.domain.Owner} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OwnerDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String phone;

    @NotNull
    private String address;

    private UserDTO user;

    public OwnerDTO() {
        // Default constructor
    }

    public OwnerDTO(com.docpet.animalhospital.domain.Owner owner) {
        this.id = owner.getId();
        this.name = owner.getName();
        this.phone = owner.getPhone();
        this.address = owner.getAddress();
        if (owner.getUser() != null) {
            this.user = new UserDTO(owner.getUser());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        if (!(o instanceof OwnerDTO)) {
            return false;
        }

        OwnerDTO ownerDTO = (OwnerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ownerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OwnerDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", address='" + getAddress() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
