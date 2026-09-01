package com.sunrisedental.repository;

import com.sunrisedental.model.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
    List<Treatment> findByActiveTrueOrderByNameAsc();
}
