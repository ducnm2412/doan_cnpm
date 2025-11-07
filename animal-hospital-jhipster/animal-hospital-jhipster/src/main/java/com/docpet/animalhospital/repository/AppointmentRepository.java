package com.docpet.animalhospital.repository;

import com.docpet.animalhospital.domain.Appointment;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Appointment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @EntityGraph(attributePaths = {"pet", "vet", "owner"})
    List<Appointment> findAll();

    @EntityGraph(attributePaths = {"pet", "vet", "owner"})
    Optional<Appointment> findOneWithEagerRelationships(Long id);

    @Query("select appointment from Appointment appointment where appointment.owner.user.login = ?#{authentication.name}")
    @EntityGraph(attributePaths = {"pet", "vet", "owner"})
    List<Appointment> findByOwnerIsCurrentUser();

    @Query("select appointment from Appointment appointment where appointment.owner.user.login = ?1")
    @EntityGraph(attributePaths = {"pet", "vet", "owner"})
    List<Appointment> findByOwner_User_Login(String login);

    @Query("select appointment from Appointment appointment where appointment.vet.user.login = ?#{authentication.name}")
    @EntityGraph(attributePaths = {"pet", "vet", "owner"})
    List<Appointment> findByVetIsCurrentUser();

    @Query("select appointment from Appointment appointment where appointment.vet.user.login = ?1")
    @EntityGraph(attributePaths = {"pet", "vet", "owner"})
    List<Appointment> findByVet_User_Login(String login);

    @Query("select appointment from Appointment appointment where appointment.pet.id = ?1")
    @EntityGraph(attributePaths = {"pet", "vet", "owner"})
    List<Appointment> findByPet_Id(Long petId);

    @Query("select appointment from Appointment appointment where appointment.vet.id = ?1")
    @EntityGraph(attributePaths = {"pet", "vet", "owner"})
    List<Appointment> findByVet_Id(Long vetId);

    @Query("select appointment from Appointment appointment where appointment.appointmentType = ?1")
    @EntityGraph(attributePaths = {"pet", "vet", "owner"})
    List<Appointment> findByAppointmentType(String appointmentType);

    @Query("select appointment from Appointment appointment where appointment.status = ?1")
    @EntityGraph(attributePaths = {"pet", "vet", "owner"})
    List<Appointment> findByStatus(String status);

    @Query("select appointment from Appointment appointment where appointment.timeStart >= ?1 and appointment.timeEnd <= ?2")
    @EntityGraph(attributePaths = {"pet", "vet", "owner"})
    List<Appointment> findByTimeRange(ZonedDateTime startTime, ZonedDateTime endTime);

    @Query("select appointment from Appointment appointment where appointment.vet.id = ?1 and appointment.timeStart >= ?2 and appointment.timeEnd <= ?3")
    @EntityGraph(attributePaths = {"pet", "vet", "owner"})
    List<Appointment> findByVetAndTimeRange(Long vetId, ZonedDateTime startTime, ZonedDateTime endTime);

    /**
     * Find appointments by date and appointment type.
     *
     * @param date the date to search for appointments.
     * @param appointmentType the appointment type (EMERGENCY, REGULAR, etc.).
     * @return the list of appointments.
     */
    @Query("select appointment from Appointment appointment where DATE(appointment.timeStart) = ?1 and appointment.appointmentType = ?2")
    @EntityGraph(attributePaths = {"pet", "vet", "owner"})
    List<Appointment> findByDateAndAppointmentType(LocalDate date, String appointmentType);

    /**
     * Find appointments by date only.
     *
     * @param date the date to search for appointments.
     * @return the list of appointments.
     */
    @Query("select appointment from Appointment appointment where DATE(appointment.timeStart) = ?1")
    @EntityGraph(attributePaths = {"pet", "vet", "owner"})
    List<Appointment> findByDate(LocalDate date);

    /**
     * Find appointments by date and appointment type for current vet.
     *
     * @param date the date to search for appointments.
     * @param appointmentType the appointment type (EMERGENCY, REGULAR, etc.).
     * @param vetLogin the vet login.
     * @return the list of appointments.
     */
    @Query("select appointment from Appointment appointment where DATE(appointment.timeStart) = ?1 and appointment.appointmentType = ?2 and appointment.vet.user.login = ?3")
    @EntityGraph(attributePaths = {"pet", "vet", "owner"})
    List<Appointment> findByDateAndAppointmentTypeAndVet(LocalDate date, String appointmentType, String vetLogin);

    /**
     * Find appointments by date for current vet.
     *
     * @param date the date to search for appointments.
     * @param vetLogin the vet login.
     * @return the list of appointments.
     */
    @Query("select appointment from Appointment appointment where DATE(appointment.timeStart) = ?1 and appointment.vet.user.login = ?2")
    @EntityGraph(attributePaths = {"pet", "vet", "owner"})
    List<Appointment> findByDateAndVet(LocalDate date, String vetLogin);
}
