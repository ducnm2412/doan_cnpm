package com.docpet.animalhospital.service.mapper;

import com.docpet.animalhospital.domain.AppointmentAction;
import com.docpet.animalhospital.service.dto.AppointmentActionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppointmentAction} and its DTO {@link AppointmentActionDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppointmentActionMapper extends EntityMapper<AppointmentActionDTO, AppointmentAction> {
    
    @Mapping(target = "appointmentId", source = "appointment.id")
    @Mapping(target = "assignedToId", source = "assignedTo.id")
    @Mapping(target = "assignedToLogin", source = "assignedTo.login")
    @Mapping(target = "createdById", source = "createdBy.id")
    @Mapping(target = "createdByLogin", source = "createdBy.login")
    AppointmentActionDTO toDto(AppointmentAction appointmentAction);

    @Mapping(target = "appointment", ignore = true)
    @Mapping(target = "assignedTo", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    AppointmentAction toEntity(AppointmentActionDTO appointmentActionDTO);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppointmentActionDTO toDtoId(AppointmentAction appointmentAction);
}



