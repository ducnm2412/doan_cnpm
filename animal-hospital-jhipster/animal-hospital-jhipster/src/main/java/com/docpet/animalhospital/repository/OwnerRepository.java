package com.docpet.animalhospital.repository;

import com.docpet.animalhospital.domain.Owner;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Owner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    @Query("select owner from Owner owner where owner.user.login = ?#{authentication.name}")
    List<Owner> findByUserIsCurrentUser();
    
    @Query("select owner from Owner owner where owner.user.login = ?1")
    Optional<Owner> findByUser_Login(String login);
}
