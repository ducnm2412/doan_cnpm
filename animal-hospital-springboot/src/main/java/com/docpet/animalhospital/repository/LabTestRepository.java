package com.docpet.animalhospital.repository;

import com.docpet.animalhospital.domain.LabTest;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface LabTestRepository extends JpaRepository<LabTest, Long> {

    @EntityGraph(attributePaths = {"appointment", "pet", "requestedBy", "assignedTo"})
    List<LabTest> findAll();

    @Query("select labTest from LabTest labTest left join fetch labTest.appointment left join fetch labTest.pet left join fetch labTest.requestedBy left join fetch labTest.assignedTo where labTest.id = :id")
    Optional<LabTest> findOneWithEagerRelationships(@Param("id") Long id);

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

