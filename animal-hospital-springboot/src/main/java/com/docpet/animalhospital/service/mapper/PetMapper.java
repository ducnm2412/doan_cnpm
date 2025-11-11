package com.docpet.animalhospital.service.mapper;

import com.docpet.animalhospital.domain.Owner;
import com.docpet.animalhospital.domain.Pet;
import com.docpet.animalhospital.service.dto.OwnerDTO;
import com.docpet.animalhospital.service.dto.PetDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PetMapper extends EntityMapper<PetDTO, Pet> {
    
    @Mapping(target = "ownerId", source = "owner.id")
    PetDTO toDto(Pet s);

    @Mapping(target = "owner", ignore = true)
    Pet toEntity(PetDTO petDTO);
}
