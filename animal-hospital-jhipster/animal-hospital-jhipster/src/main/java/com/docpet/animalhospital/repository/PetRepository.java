package com.docpet.animalhospital.repository;

import com.docpet.animalhospital.domain.Pet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Spring Data JPA repository for the Pet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    
    @Query("select pet from Pet pet where pet.owner.user.login = ?#{authentication.name}")
    List<Pet> findByOwnerIsCurrentUser();
    
    @Query("select pet from Pet pet where pet.owner.user.login = ?1")
    List<Pet> findByOwner_User_Login(String login);
}
