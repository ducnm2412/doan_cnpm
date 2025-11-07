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

/**
 * Mapper for the entity {@link Appointment} and its DTO {@link AppointmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppointmentMapper extends EntityMapper<AppointmentDTO, Appointment> {

    @Mapping(target = "pet", source = "pet", qualifiedByName = "petId")
    @Mapping(target = "vet", source = "vet", qualifiedByName = "vetId")
    @Mapping(target = "owner", source = "owner", qualifiedByName = "ownerId")
    AppointmentDTO toDto(Appointment s);

    @Named("petId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PetDTO toDtoPetId(Pet pet);

    @Named("vetId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VetDTO toDtoVetId(Vet vet);

    @Named("ownerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OwnerDTO toDtoOwnerId(Owner owner);
}
