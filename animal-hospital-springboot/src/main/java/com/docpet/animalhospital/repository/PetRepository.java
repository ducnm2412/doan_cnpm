package com.docpet.animalhospital.repository;

import com.docpet.animalhospital.domain.Pet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query("select pet from Pet pet where pet.owner.user.login = ?#{authentication.name}")
    List<Pet> findByOwnerIsCurrentUser();
    
    @Query("select pet from Pet pet where pet.owner.user.login = ?1")
    List<Pet> findByOwner_User_Login(String login);
    
    @Query("select pet from Pet pet left join fetch pet.owner left join fetch pet.owner.user where pet.id = ?1")
    Optional<Pet> findByIdWithOwner(Long id);
}

