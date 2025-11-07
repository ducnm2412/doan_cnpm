package com.docpet.animalhospital.service.mapper;

import com.docpet.animalhospital.domain.User;
import com.docpet.animalhospital.domain.Vet;
import com.docpet.animalhospital.service.dto.UserDTO;
import com.docpet.animalhospital.service.dto.VetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vet} and its DTO {@link VetDTO}.
 */
@Mapper(componentModel = "spring")
public interface VetMapper extends EntityMapper<VetDTO, Vet> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    VetDTO toDto(Vet s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
