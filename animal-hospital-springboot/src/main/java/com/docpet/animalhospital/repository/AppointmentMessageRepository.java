package com.docpet.animalhospital.repository;

import com.docpet.animalhospital.domain.AppointmentMessage;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentMessageRepository extends JpaRepository<AppointmentMessage, Long> {

    @Query("select am from AppointmentMessage am where am.appointment.id = :appointmentId order by am.timestamp asc")
    @EntityGraph(attributePaths = {"appointment", "sender"})
    List<AppointmentMessage> findByAppointmentIdOrderByTimestampAsc(@Param("appointmentId") Long appointmentId);

    @Query("select am from AppointmentMessage am where am.appointment.id = :appointmentId order by am.timestamp desc")
    @EntityGraph(attributePaths = {"appointment", "sender"})
    List<AppointmentMessage> findByAppointmentIdOrderByTimestampDesc(@Param("appointmentId") Long appointmentId);
}


