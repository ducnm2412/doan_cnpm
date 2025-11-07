package com.docpet.animalhospital.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Pet.
 */
@Entity
@Table(name = "pet")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "species", nullable = false)
    private String species;

    @Column(name = "breed")
    private String breed;

    @Column(name = "sex")
    private String sex;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "weight", precision = 21, scale = 2)
    private BigDecimal weight;

    @Column(name = "allergies")
    private String allergies;

    @Column(name = "notes")
    private String notes;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Pet name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return this.species;
    }

    public Pet species(String species) {
        this.setSpecies(species);
        return this;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return this.breed;
    }

    public Pet breed(String breed) {
        this.setBreed(breed);
        return this;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSex() {
        return this.sex;
    }

    public Pet sex(String sex) {
        this.setSex(sex);
        return this;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Pet dateOfBirth(LocalDate dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public BigDecimal getWeight() {
        return this.weight;
    }

    public Pet weight(BigDecimal weight) {
        this.setWeight(weight);
        return this;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getAllergies() {
        return this.allergies;
    }

    public Pet allergies(String allergies) {
        this.setAllergies(allergies);
        return this;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getNotes() {
        return this.notes;
    }

    public Pet notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Pet imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Owner getOwner() {
        return this.owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Pet owner(Owner owner) {
        this.setOwner(owner);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pet)) {
            return false;
        }
        return getId() != null && getId().equals(((Pet) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pet{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", species='" + getSpecies() + "'" +
            ", breed='" + getBreed() + "'" +
            ", sex='" + getSex() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", weight=" + getWeight() +
            ", allergies='" + getAllergies() + "'" +
            ", notes='" + getNotes() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            "}";
    }
}
