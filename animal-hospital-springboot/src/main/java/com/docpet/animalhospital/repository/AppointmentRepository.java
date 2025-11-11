package com.docpet.animalhospital.repository;

import com.docpet.animalhospital.domain.Appointment;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @EntityGraph(attributePaths = {"pet", "vet", "owner"})
    List<Appointment> findAll();

    @Query("select appointment from Appointment appointment left join fetch appointment.pet left join fetch appointment.vet left join fetch appointment.owner where appointment.id = :id")
    Optional<Appointment> findOneWithEagerRelationships(@Param("id") Long id);

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

    @Query("select appointment from Appointment appointment where appointment.vet.id = ?1 and appointment.timeStart = ?2")
    @EntityGraph(attributePaths = {"pet", "vet", "owner"})
    List<Appointment> findByVetAndTimeStart(Long vetId, ZonedDateTime startTime);

    @Query("select appointment from Appointment appointment " +
           "where appointment.vet.id = ?1 " +
           "and appointment.status NOT IN ('CANCELLED', 'REJECTED') " +
           "and (appointment.timeStart < ?3 and (appointment.timeEnd is null or appointment.timeEnd > ?2))")
    @EntityGraph(attributePaths = {"pet", "vet", "owner"})
    List<Appointment> findOverlappingAppointmentsForVet(Long vetId, ZonedDateTime startTime, ZonedDateTime endTime);

    @Query("select appointment from Appointment appointment " +
           "where appointment.vet.id = ?1 " +
           "and appointment.status NOT IN ('CANCELLED', 'REJECTED') " +
           "and DATE(appointment.timeStart) = DATE(?2)")
    @EntityGraph(attributePaths = {"pet", "vet", "owner"})
    List<Appointment> findAppointmentsByVetAndDate(Long vetId, ZonedDateTime date);

    @Query("select appointment from Appointment appointment where DATE(appointment.timeStart) = ?1 and appointment.appointmentType = ?2")
    @EntityGraph(attributePaths = {"pet", "vet", "owner"})
    List<Appointment> findByDateAndAppointmentType(LocalDate date, String appointmentType);

    @Query("select appointment from Appointment appointment where DATE(appointment.timeStart) = ?1")
    @EntityGraph(attributePaths = {"pet", "vet", "owner"})
    List<Appointment> findByDate(LocalDate date);

    @Query("select appointment from Appointment appointment where DATE(appointment.timeStart) = ?1 and appointment.appointmentType = ?2 and appointment.vet.user.login = ?3")
    @EntityGraph(attributePaths = {"pet", "vet", "owner"})
    List<Appointment> findByDateAndAppointmentTypeAndVet(LocalDate date, String appointmentType, String vetLogin);

    @Query("select appointment from Appointment appointment where DATE(appointment.timeStart) = ?1 and appointment.vet.user.login = ?2")
    @EntityGraph(attributePaths = {"pet", "vet", "owner"})
    List<Appointment> findByDateAndVet(LocalDate date, String vetLogin);
}

