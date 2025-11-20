package com.docpet.animalhospital.repository;

import com.docpet.animalhospital.domain.Owner;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    @Query("select owner from Owner owner where owner.user.login = ?#{authentication.name}")
    List<Owner> findByUserIsCurrentUser();
    
    @Query("select owner from Owner owner where owner.user.login = ?1")
    List<Owner> findAllByUser_Login(String login);
    
    @Query("select owner from Owner owner where owner.user.login = ?1 order by owner.id asc")
    java.util.List<Owner> findOwnersByUser_Login(String login);
    
    default Optional<Owner> findFirstByUser_Login(String login) {
        java.util.List<Owner> owners = findOwnersByUser_Login(login);
        return owners.isEmpty() ? Optional.empty() : Optional.of(owners.get(0));
    }
    
    // Alias method để tương thích với code hiện tại
    default Optional<Owner> findByUser_Login(String login) {
        return findFirstByUser_Login(login);
    }
    
    @Query("select owner from Owner owner left join fetch owner.user where owner.id = ?1")
    Optional<Owner> findByIdWithUser(Long id);
}

