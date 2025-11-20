package com.docpet.animalhospital.service.mapper;

import com.docpet.animalhospital.domain.Owner;
import com.docpet.animalhospital.domain.Pet;
import com.docpet.animalhospital.service.dto.PetDTO;
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
public class PetMapperImpl implements PetMapper {

    @Override
    public List<Pet> toEntity(List<PetDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Pet> list = new ArrayList<Pet>( dtoList.size() );
        for ( PetDTO petDTO : dtoList ) {
            list.add( toEntity( petDTO ) );
        }

        return list;
    }

    @Override
    public List<PetDTO> toDto(List<Pet> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PetDTO> list = new ArrayList<PetDTO>( entityList.size() );
        for ( Pet pet : entityList ) {
            list.add( toDto( pet ) );
        }

        return list;
    }

    @Override
    public PetDTO toDto(Pet s) {
        if ( s == null ) {
            return null;
        }

        PetDTO petDTO = new PetDTO();

        petDTO.setOwnerId( sOwnerId( s ) );
        petDTO.setId( s.getId() );
        petDTO.setName( s.getName() );
        petDTO.setSpecies( s.getSpecies() );
        petDTO.setBreed( s.getBreed() );
        petDTO.setSex( s.getSex() );
        petDTO.setDateOfBirth( s.getDateOfBirth() );
        petDTO.setWeight( s.getWeight() );
        petDTO.setAllergies( s.getAllergies() );
        petDTO.setNotes( s.getNotes() );
        petDTO.setImageUrl( s.getImageUrl() );

        return petDTO;
    }

    @Override
    public Pet toEntity(PetDTO petDTO) {
        if ( petDTO == null ) {
            return null;
        }

        Pet pet = new Pet();

        pet.setId( petDTO.getId() );
        pet.setName( petDTO.getName() );
        pet.setSpecies( petDTO.getSpecies() );
        pet.setBreed( petDTO.getBreed() );
        pet.setSex( petDTO.getSex() );
        pet.setDateOfBirth( petDTO.getDateOfBirth() );
        pet.setWeight( petDTO.getWeight() );
        pet.setAllergies( petDTO.getAllergies() );
        pet.setNotes( petDTO.getNotes() );
        pet.setImageUrl( petDTO.getImageUrl() );

        return pet;
    }

    private Long sOwnerId(Pet pet) {
        Owner owner = pet.getOwner();
        if ( owner == null ) {
            return null;
        }
        return owner.getId();
    }
}
