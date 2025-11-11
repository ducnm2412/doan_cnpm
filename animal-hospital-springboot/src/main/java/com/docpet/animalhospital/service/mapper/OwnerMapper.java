package com.docpet.animalhospital.service.mapper;

import com.docpet.animalhospital.domain.Owner;
import com.docpet.animalhospital.domain.User;
import com.docpet.animalhospital.service.dto.OwnerDTO;
import com.docpet.animalhospital.service.dto.UserDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OwnerMapper extends EntityMapper<OwnerDTO, Owner> {
    @Mapping(target = "userId", source = "user.id")
    OwnerDTO toDto(Owner s);

    @Mapping(target = "user", ignore = true)
    Owner toEntity(OwnerDTO ownerDTO);
}
