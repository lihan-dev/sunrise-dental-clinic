package com.sunrisedental.service;

import com.sunrisedental.model.Appointment;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {

    private final AppointmentService appointmentService;
    private final BillingService billingService;

    public ReportService(AppointmentService appointmentService, BillingService billingService) {
        this.appointmentService = appointmentService;
        this.billingService = billingService;
    }

    public List<Appointment> dailyAppointments(LocalDate date) {
        return appointmentService.getByDate(date);
    }

    public BigDecimal totalRevenue() {
        return billingService.totalRevenue();
    }
}
