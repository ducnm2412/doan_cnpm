package com.docpet.animalhospital.domain;

import com.docpet.animalhospital.domain.enumeration.SeverityLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * Entity cho Bệnh của Rắn
 */
@Entity
@Table(name = "disease_snake")
public class DiseaseSnake implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 500)
    @Column(name = "title", nullable = false, length = 500)
    private String title; // "Bệnh nôn ở rắn"

    @Size(max = 1000)
    @Column(name = "keywords", length = 1000)
    private String keywords; // "nôn,tiêu chảy,rắn"

    @NotNull
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity_level", length = 50)
    private SeverityLevel severityLevel;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @NotNull
    @Column(name = "created_date", nullable = false, updatable = false)
    private Instant createdDate;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    public DiseaseSnake() {
        this.createdDate = Instant.now();
        this.lastModifiedDate = Instant.now();
        this.isActive = true;
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdDate == null) {
            this.createdDate = Instant.now();
        }
        if (this.lastModifiedDate == null) {
            this.lastModifiedDate = Instant.now();
        }
        if (this.isActive == null) {
            this.isActive = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastModifiedDate = Instant.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SeverityLevel getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(SeverityLevel severityLevel) {
        this.severityLevel = severityLevel;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiseaseSnake)) return false;
        return getId() != null && getId().equals(((DiseaseSnake) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "DiseaseSnake{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", severityLevel='" + getSeverityLevel() + "'" +
            ", isActive=" + getIsActive() +
            "}";
    }
}
