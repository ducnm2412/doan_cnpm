package com.docpet.animalhospital.service.mapper;

import com.docpet.animalhospital.domain.User;
import com.docpet.animalhospital.domain.Vet;
import com.docpet.animalhospital.service.dto.VetDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-20T10:59:39+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Eclipse Adoptium)"
)
@Component
public class VetMapperImpl implements VetMapper {

    @Override
    public List<Vet> toEntity(List<VetDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Vet> list = new ArrayList<Vet>( dtoList.size() );
        for ( VetDTO vetDTO : dtoList ) {
            list.add( toEntity( vetDTO ) );
        }

        return list;
    }

    @Override
    public List<VetDTO> toDto(List<Vet> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<VetDTO> list = new ArrayList<VetDTO>( entityList.size() );
        for ( Vet vet : entityList ) {
            list.add( toDto( vet ) );
        }

        return list;
    }

    @Override
    public VetDTO toDto(Vet s) {
        if ( s == null ) {
            return null;
        }

        VetDTO vetDTO = new VetDTO();

        vetDTO.setUserId( sUserId( s ) );
        vetDTO.setId( s.getId() );
        vetDTO.setLicenseNo( s.getLicenseNo() );
        vetDTO.setSpecialization( s.getSpecialization() );

        return vetDTO;
    }

    @Override
    public Vet toEntity(VetDTO vetDTO) {
        if ( vetDTO == null ) {
            return null;
        }

        Vet vet = new Vet();

        vet.setId( vetDTO.getId() );
        vet.setLicenseNo( vetDTO.getLicenseNo() );
        vet.setSpecialization( vetDTO.getSpecialization() );

        return vet;
    }

    private Long sUserId(Vet vet) {
        User user = vet.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }
}
