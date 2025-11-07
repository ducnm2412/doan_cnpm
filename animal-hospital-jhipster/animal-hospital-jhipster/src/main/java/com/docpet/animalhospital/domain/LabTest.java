package com.docpet.animalhospital.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A LabTest.
 */
@Entity
@Table(name = "lab_test")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LabTest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "test_name", nullable = false)
    private String testName;

    @NotNull
    @Column(name = "test_type", nullable = false)
    private String testType; // BLOOD, URINE, STOOL, XRAY, ULTRASOUND, etc.

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status; // REQUESTED, IN_PROGRESS, COMPLETED, CANCELLED

    @Column(name = "result")
    private String result;

    @Column(name = "notes")
    private String notes;

    @Column(name = "requested_date")
    private ZonedDateTime requestedDate;

    @Column(name = "completed_date")
    private ZonedDateTime completedDate;

    @Column(name = "sample_collected_date")
    private ZonedDateTime sampleCollectedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_by_id")
    private User requestedBy; // Vet yêu cầu

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to_id")
    private User assignedTo; // Assistant được phân công lấy mẫu

    // Constructors
    public LabTest() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ZonedDateTime getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(ZonedDateTime requestedDate) {
        this.requestedDate = requestedDate;
    }

    public ZonedDateTime getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(ZonedDateTime completedDate) {
        this.completedDate = completedDate;
    }

    public ZonedDateTime getSampleCollectedDate() {
        return sampleCollectedDate;
    }

    public void setSampleCollectedDate(ZonedDateTime sampleCollectedDate) {
        this.sampleCollectedDate = sampleCollectedDate;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public User getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(User requestedBy) {
        this.requestedBy = requestedBy;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LabTest)) return false;
        LabTest that = (LabTest) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "LabTest{" +
            "id=" + id +
            ", testName='" + testName + '\'' +
            ", testType='" + testType + '\'' +
            ", status='" + status + '\'' +
            '}';
    }
}



