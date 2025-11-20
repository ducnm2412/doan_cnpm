package com.docpet.animalhospital.service.mapper;

import com.docpet.animalhospital.domain.Owner;
import com.docpet.animalhospital.domain.User;
import com.docpet.animalhospital.service.dto.OwnerDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-20T12:16:51+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Eclipse Adoptium)"
)
@Component
public class OwnerMapperImpl implements OwnerMapper {

    @Override
    public List<Owner> toEntity(List<OwnerDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Owner> list = new ArrayList<Owner>( dtoList.size() );
        for ( OwnerDTO ownerDTO : dtoList ) {
            list.add( toEntity( ownerDTO ) );
        }

        return list;
    }

    @Override
    public List<OwnerDTO> toDto(List<Owner> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<OwnerDTO> list = new ArrayList<OwnerDTO>( entityList.size() );
        for ( Owner owner : entityList ) {
            list.add( toDto( owner ) );
        }

        return list;
    }

    @Override
    public OwnerDTO toDto(Owner s) {
        if ( s == null ) {
            return null;
        }

        OwnerDTO ownerDTO = new OwnerDTO();

        ownerDTO.setUserId( sUserId( s ) );
        ownerDTO.setId( s.getId() );
        ownerDTO.setName( s.getName() );
        ownerDTO.setPhone( s.getPhone() );
        ownerDTO.setAddress( s.getAddress() );

        ownerDTO.setFirstName( splitFirstName(s) );
        ownerDTO.setLastName( splitLastName(s) );

        return ownerDTO;
    }

    @Override
    public Owner toEntity(OwnerDTO ownerDTO) {
        if ( ownerDTO == null ) {
            return null;
        }

        Owner owner = new Owner();

        owner.setId( ownerDTO.getId() );
        owner.setPhone( ownerDTO.getPhone() );
        owner.setAddress( ownerDTO.getAddress() );

        owner.setName( combineName(ownerDTO) );

        return owner;
    }

    private Long sUserId(Owner owner) {
        User user = owner.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }
}
