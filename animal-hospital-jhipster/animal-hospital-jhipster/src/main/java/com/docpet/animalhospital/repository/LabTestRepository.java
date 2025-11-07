package com.docpet.animalhospital.repository;

import com.docpet.animalhospital.domain.LabTest;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the LabTest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LabTestRepository extends JpaRepository<LabTest, Long> {

    @EntityGraph(attributePaths = {"appointment", "pet", "requestedBy", "assignedTo"})
    List<LabTest> findAll();

    @EntityGraph(attributePaths = {"appointment", "pet", "requestedBy", "assignedTo"})
    Optional<LabTest> findOneWithEagerRelationships(Long id);

    @Query("select labTest from LabTest labTest where labTest.appointment.id = ?1")
    @EntityGraph(attributePaths = {"appointment", "pet", "requestedBy", "assignedTo"})
    List<LabTest> findByAppointment_Id(Long appointmentId);

    @Query("select labTest from LabTest labTest where labTest.pet.id = ?1")
    @EntityGraph(attributePaths = {"appointment", "pet", "requestedBy", "assignedTo"})
    List<LabTest> findByPet_Id(Long petId);

    @Query("select labTest from LabTest labTest where labTest.requestedBy.login = ?1")
    @EntityGraph(attributePaths = {"appointment", "pet", "requestedBy", "assignedTo"})
    List<LabTest> findByRequestedBy_Login(String requestedByLogin);

    @Query("select labTest from LabTest labTest where labTest.assignedTo.login = ?1")
    @EntityGraph(attributePaths = {"appointment", "pet", "requestedBy", "assignedTo"})
    List<LabTest> findByAssignedTo_Login(String assignedToLogin);

    @Query("select labTest from LabTest labTest where labTest.testType = ?1")
    @EntityGraph(attributePaths = {"appointment", "pet", "requestedBy", "assignedTo"})
    List<LabTest> findByTestType(String testType);

    @Query("select labTest from LabTest labTest where labTest.status = ?1")
    @EntityGraph(attributePaths = {"appointment", "pet", "requestedBy", "assignedTo"})
    List<LabTest> findByStatus(String status);

    @Query("select labTest from LabTest labTest where labTest.appointment.id = ?1 and labTest.status = ?2")
    @EntityGraph(attributePaths = {"appointment", "pet", "requestedBy", "assignedTo"})
    List<LabTest> findByAppointment_IdAndStatus(Long appointmentId, String status);

    @Query("select labTest from LabTest labTest where labTest.assignedTo.login = ?1 and labTest.status = ?2")
    @EntityGraph(attributePaths = {"appointment", "pet", "requestedBy", "assignedTo"})
    List<LabTest> findByAssignedTo_LoginAndStatus(String assignedToLogin, String status);
}



