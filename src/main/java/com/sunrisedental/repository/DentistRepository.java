package com.sunrisedental.repository;

import com.sunrisedental.model.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DentistRepository extends JpaRepository<Dentist, Long> {
    List<Dentist> findByActiveTrueOrderByNameAsc();
}
