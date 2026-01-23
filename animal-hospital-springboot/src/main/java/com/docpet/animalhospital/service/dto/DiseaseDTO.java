package com.docpet.animalhospital.service.dto;

import com.docpet.animalhospital.domain.enumeration.SeverityLevel;
import java.io.Serializable;
import java.time.Instant;

/**
 * DTO chung cho tất cả các loài động vật (Chó, Mèo, Chuột, Chim, Cá, Rùa, Rắn, Khỉ, Heo, Dê, Cừu, Trâu, Bò)
 */
public class DiseaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String keywords;
    private String content;
    private SeverityLevel severityLevel;
    private Boolean isActive;
    private Instant createdDate;
    private Instant lastModifiedDate;
    private String species; // Tên loài: "Chó", "Mèo", "Chuột", "Chim", "Cá", "Rùa", "Rắn", "Khỉ", "Heo", "Dê", "Cừu", "Trâu", "Bò"

    public DiseaseDTO() {}

    public DiseaseDTO(Long id, String title, String keywords, String content, 
                     SeverityLevel severityLevel, String species) {
        this.id = id;
        this.title = title;
        this.keywords = keywords;
        this.content = content;
        this.severityLevel = severityLevel;
        this.species = species;
        this.isActive = true;
        this.createdDate = Instant.now();
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

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    @Override
    public String toString() {
        return "DiseaseDTO{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", species='" + species + '\'' +
            ", severityLevel=" + severityLevel +
            '}';
    }
}
