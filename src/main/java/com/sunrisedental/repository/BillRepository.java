package com.sunrisedental.repository;

import com.sunrisedental.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findByAppointment_AppointmentNumber(String appointmentNumber);
    Optional<Bill> findByBillNumber(String billNumber);
}
