package com.docpet.animalhospital.service.mapper;

import com.docpet.animalhospital.domain.Appointment;
import com.docpet.animalhospital.domain.Owner;
import com.docpet.animalhospital.domain.Pet;
import com.docpet.animalhospital.domain.Vet;
import com.docpet.animalhospital.service.dto.AppointmentDTO;
import com.docpet.animalhospital.service.dto.OwnerDTO;
import com.docpet.animalhospital.service.dto.PetDTO;
import com.docpet.animalhospital.service.dto.VetDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {PetMapper.class, VetMapper.class, OwnerMapper.class})
public interface AppointmentMapper extends EntityMapper<AppointmentDTO, Appointment> {

    @Mapping(target = "pet", source = "pet")
    @Mapping(target = "vet", source = "vet")
    @Mapping(target = "owner", source = "owner")
    AppointmentDTO toDto(Appointment s);

    @Mapping(target = "pet", ignore = true)
    @Mapping(target = "vet", ignore = true)
    @Mapping(target = "owner", ignore = true)
    Appointment toEntity(AppointmentDTO appointmentDTO);

    default Appointment partialUpdate(Appointment existing, AppointmentDTO dto) {
        if (dto.getTimeStart() != null) {
            existing.setTimeStart(dto.getTimeStart());
        }
        if (dto.getTimeEnd() != null) {
            existing.setTimeEnd(dto.getTimeEnd());
        }
        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus());
        }
        if (dto.getNotes() != null) {
            existing.setNotes(dto.getNotes());
        }
        if (dto.getAppointmentType() != null) {
            existing.setAppointmentType(dto.getAppointmentType());
        }
        if (dto.getLocationType() != null) {
            existing.setLocationType(dto.getLocationType());
        }
        return existing;
    }

    default Appointment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Appointment appointment = new Appointment();
        appointment.setId(id);
        return appointment;
    }
}

