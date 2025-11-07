package com.docpet.animalhospital.repository;

import com.docpet.animalhospital.domain.Vet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VetRepository extends JpaRepository<Vet, Long> {
    @Query("select vet from Vet vet where vet.user.login = ?#{authentication.name}")
    List<Vet> findByUserIsCurrentUser();
    
    @Query("select vet from Vet vet where vet.user.login = ?1")
    Optional<Vet> findByUser_Login(String login);
}
