package com.docpet.animalhospital.repository;

import com.docpet.animalhospital.domain.AppointmentAction;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the AppointmentAction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppointmentActionRepository extends JpaRepository<AppointmentAction, Long> {

    @EntityGraph(attributePaths = {"appointment", "assignedTo", "createdBy"})
    List<AppointmentAction> findAll();

    @EntityGraph(attributePaths = {"appointment", "assignedTo", "createdBy"})
    Optional<AppointmentAction> findOneWithEagerRelationships(Long id);

    @Query("select appointmentAction from AppointmentAction appointmentAction where appointmentAction.appointment.id = ?1")
    @EntityGraph(attributePaths = {"appointment", "assignedTo", "createdBy"})
    List<AppointmentAction> findByAppointment_Id(Long appointmentId);

    @Query("select appointmentAction from AppointmentAction appointmentAction where appointmentAction.assignedTo.login = ?1")
    @EntityGraph(attributePaths = {"appointment", "assignedTo", "createdBy"})
    List<AppointmentAction> findByAssignedTo_Login(String assignedToLogin);

    @Query("select appointmentAction from AppointmentAction appointmentAction where appointmentAction.createdBy.login = ?1")
    @EntityGraph(attributePaths = {"appointment", "assignedTo", "createdBy"})
    List<AppointmentAction> findByCreatedBy_Login(String createdByLogin);

    @Query("select appointmentAction from AppointmentAction appointmentAction where appointmentAction.actionType = ?1")
    @EntityGraph(attributePaths = {"appointment", "assignedTo", "createdBy"})
    List<AppointmentAction> findByActionType(String actionType);

    @Query("select appointmentAction from AppointmentAction appointmentAction where appointmentAction.status = ?1")
    @EntityGraph(attributePaths = {"appointment", "assignedTo", "createdBy"})
    List<AppointmentAction> findByStatus(String status);

    @Query("select appointmentAction from AppointmentAction appointmentAction where appointmentAction.appointment.id = ?1 and appointmentAction.actionType = ?2")
    @EntityGraph(attributePaths = {"appointment", "assignedTo", "createdBy"})
    List<AppointmentAction> findByAppointment_IdAndActionType(Long appointmentId, String actionType);

    @Query("select appointmentAction from AppointmentAction appointmentAction where appointmentAction.appointment.id = ?1 and appointmentAction.status = ?2")
    @EntityGraph(attributePaths = {"appointment", "assignedTo", "createdBy"})
    List<AppointmentAction> findByAppointment_IdAndStatus(Long appointmentId, String status);

    @Query("select appointmentAction from AppointmentAction appointmentAction where appointmentAction.assignedTo.login = ?1 and appointmentAction.status = ?2")
    @EntityGraph(attributePaths = {"appointment", "assignedTo", "createdBy"})
    List<AppointmentAction> findByAssignedTo_LoginAndStatus(String assignedToLogin, String status);

    @Query("select appointmentAction from AppointmentAction appointmentAction where appointmentAction.assignedTo.id = ?1")
    @EntityGraph(attributePaths = {"appointment", "assignedTo", "createdBy"})
    List<AppointmentAction> findByAssignedTo_Id(Long assignedToId);

    @Query("select appointmentAction from AppointmentAction appointmentAction where appointmentAction.assignedTo.id = ?1 and appointmentAction.status = ?2")
    @EntityGraph(attributePaths = {"appointment", "assignedTo", "createdBy"})
    List<AppointmentAction> findByAssignedTo_IdAndStatus(Long assignedToId, String status);
}



