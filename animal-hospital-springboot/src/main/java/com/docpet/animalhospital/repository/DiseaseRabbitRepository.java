package com.docpet.animalhospital.repository;

import com.docpet.animalhospital.domain.DiseaseRabbit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRabbitRepository extends JpaRepository<DiseaseRabbit, Long> {

    @Query("SELECT d FROM DiseaseRabbit d WHERE " +
           "(d.isActive = true OR d.isActive IS NULL) AND (" +
           "LOWER(d.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(d.keywords) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(d.content) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
           ") ORDER BY " +
           "CASE WHEN LOWER(d.title) LIKE LOWER(CONCAT('%', :keyword, '%')) THEN 1 ELSE 2 END, " +
           "CASE WHEN LOWER(d.keywords) LIKE LOWER(CONCAT('%', :keyword, '%')) THEN 1 ELSE 2 END")
    List<DiseaseRabbit> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
