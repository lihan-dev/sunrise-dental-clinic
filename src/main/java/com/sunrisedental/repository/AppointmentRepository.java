package com.sunrisedental.repository;

import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByAppointmentNumber(String appointmentNumber);
    boolean existsByDentistAndAppointmentDateAndAppointmentTime(Dentist dentist, LocalDate date, LocalTime time);
    List<Appointment> findByAppointmentDateOrderByAppointmentTimeAsc(LocalDate date);
    List<Appointment> findTop10ByOrderByCreatedAtDesc();
    long countByAppointmentDate(LocalDate date);
}
