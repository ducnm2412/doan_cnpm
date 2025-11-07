package com.docpet.animalhospital.service.mapper;

import com.docpet.animalhospital.domain.LabTest;
import com.docpet.animalhospital.service.dto.LabTestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LabTest} and its DTO {@link LabTestDTO}.
 */
@Mapper(componentModel = "spring")
public interface LabTestMapper extends EntityMapper<LabTestDTO, LabTest> {
    
    @Mapping(target = "appointmentId", source = "appointment.id")
    @Mapping(target = "petId", source = "pet.id")
    @Mapping(target = "petName", source = "pet.name")
    @Mapping(target = "requestedById", source = "requestedBy.id")
    @Mapping(target = "requestedByLogin", source = "requestedBy.login")
    @Mapping(target = "assignedToId", source = "assignedTo.id")
    @Mapping(target = "assignedToLogin", source = "assignedTo.login")
    LabTestDTO toDto(LabTest labTest);

    @Mapping(target = "appointment", ignore = true)
    @Mapping(target = "pet", ignore = true)
    @Mapping(target = "requestedBy", ignore = true)
    @Mapping(target = "assignedTo", ignore = true)
    LabTest toEntity(LabTestDTO labTestDTO);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LabTestDTO toDtoId(LabTest labTest);
}



