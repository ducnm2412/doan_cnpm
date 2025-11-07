package com.docpet.animalhospital.service.mapper;

import com.docpet.animalhospital.domain.Owner;
import com.docpet.animalhospital.domain.User;
import com.docpet.animalhospital.service.dto.OwnerDTO;
import com.docpet.animalhospital.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Owner} and its DTO {@link OwnerDTO}.
 */
@Mapper(componentModel = "spring")
public interface OwnerMapper extends EntityMapper<OwnerDTO, Owner> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    OwnerDTO toDto(Owner s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
