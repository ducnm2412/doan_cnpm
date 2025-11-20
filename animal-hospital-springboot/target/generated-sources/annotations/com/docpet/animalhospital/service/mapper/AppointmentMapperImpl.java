package com.docpet.animalhospital.service.mapper;

import com.docpet.animalhospital.domain.Appointment;
import com.docpet.animalhospital.service.dto.AppointmentDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-20T12:16:51+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Eclipse Adoptium)"
)
@Component
public class AppointmentMapperImpl implements AppointmentMapper {

    @Autowired
    private PetMapper petMapper;
    @Autowired
    private VetMapper vetMapper;
    @Autowired
    private OwnerMapper ownerMapper;

    @Override
    public List<Appointment> toEntity(List<AppointmentDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Appointment> list = new ArrayList<Appointment>( dtoList.size() );
        for ( AppointmentDTO appointmentDTO : dtoList ) {
            list.add( toEntity( appointmentDTO ) );
        }

        return list;
    }

    @Override
    public List<AppointmentDTO> toDto(List<Appointment> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AppointmentDTO> list = new ArrayList<AppointmentDTO>( entityList.size() );
        for ( Appointment appointment : entityList ) {
            list.add( toDto( appointment ) );
        }

        return list;
    }

    @Override
    public AppointmentDTO toDto(Appointment s) {
        if ( s == null ) {
            return null;
        }

        AppointmentDTO appointmentDTO = new AppointmentDTO();

        appointmentDTO.setPet( petMapper.toDto( s.getPet() ) );
        appointmentDTO.setVet( vetMapper.toDto( s.getVet() ) );
        appointmentDTO.setOwner( ownerMapper.toDto( s.getOwner() ) );
        appointmentDTO.setId( s.getId() );
        appointmentDTO.setTimeStart( s.getTimeStart() );
        appointmentDTO.setTimeEnd( s.getTimeEnd() );
        appointmentDTO.setType( s.getType() );
        appointmentDTO.setStatus( s.getStatus() );
        appointmentDTO.setNotes( s.getNotes() );
        appointmentDTO.setAppointmentType( s.getAppointmentType() );
        appointmentDTO.setLocationType( s.getLocationType() );

        return appointmentDTO;
    }

    @Override
    public Appointment toEntity(AppointmentDTO appointmentDTO) {
        if ( appointmentDTO == null ) {
            return null;
        }

        Appointment appointment = new Appointment();

        appointment.setId( appointmentDTO.getId() );
        appointment.setTimeStart( appointmentDTO.getTimeStart() );
        appointment.setTimeEnd( appointmentDTO.getTimeEnd() );
        appointment.setType( appointmentDTO.getType() );
        appointment.setStatus( appointmentDTO.getStatus() );
        appointment.setNotes( appointmentDTO.getNotes() );
        appointment.setAppointmentType( appointmentDTO.getAppointmentType() );
        appointment.setLocationType( appointmentDTO.getLocationType() );

        return appointment;
    }
}
