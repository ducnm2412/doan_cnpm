package com.docpet.animalhospital.repository;

import com.docpet.animalhospital.domain.Vet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VetRepository extends JpaRepository<Vet, Long> {
    @Query("select vet from Vet vet where vet.user.login = ?#{authentication.name}")
    List<Vet> findByUserIsCurrentUser();
    
    @Query("select vet from Vet vet where vet.user.login = ?1")
    List<Vet> findAllByUser_Login(String login);
    
    @Query("select vet from Vet vet where vet.user.login = ?1 order by vet.id asc")
    java.util.List<Vet> findVetsByUser_Login(String login);
    
    default Optional<Vet> findFirstByUser_Login(String login) {
        java.util.List<Vet> vets = findVetsByUser_Login(login);
        return vets.isEmpty() ? Optional.empty() : Optional.of(vets.get(0));
    }
    
    // Alias method để tương thích với code hiện tại
    default Optional<Vet> findByUser_Login(String login) {
        return findFirstByUser_Login(login);
    }
}

