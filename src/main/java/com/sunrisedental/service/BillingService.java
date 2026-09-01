package com.sunrisedental.service;

import com.sunrisedental.exception.ResourceNotFoundException;
import com.sunrisedental.model.Appointment;
import com.sunrisedental.model.Bill;
import com.sunrisedental.repository.BillRepository;
import com.sunrisedental.service.pricing.BillingStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@Transactional
public class BillingService {

    public static final BigDecimal CONSULTATION_FEE = new BigDecimal("1500.00");

    private final BillRepository billRepository;
    private final AppointmentService appointmentService;
    private final BillingStrategy billingStrategy;

    public BillingService(
            BillRepository billRepository,
            AppointmentService appointmentService,
            BillingStrategy billingStrategy) {
        this.billRepository = billRepository;
        this.appointmentService = appointmentService;
        this.billingStrategy = billingStrategy;
    }

    public Bill generateForAppointment(String appointmentNumber) {
        return billRepository.findByAppointment_AppointmentNumber(appointmentNumber)
                .orElseGet(() -> {
                    Appointment appointment = appointmentService.getByNumber(appointmentNumber);
                    BigDecimal treatmentFee = appointment.getTreatment().getTreatmentFee();
                    BigDecimal total = billingStrategy.calculateTotal(treatmentFee, CONSULTATION_FEE);

                    Bill bill = new Bill();
                    bill.setBillNumber(generateBillNumber());
                    bill.setAppointment(appointment);
                    bill.setTreatmentFee(treatmentFee);
                    bill.setConsultationFee(CONSULTATION_FEE);
                    bill.setTotalAmount(total);
                    return billRepository.save(bill);
                });
    }

    @Transactional(readOnly = true)
    public Bill findByBillNumber(String billNumber) {
        return billRepository.findByBillNumber(billNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found: " + billNumber));
    }

    @Transactional(readOnly = true)
    public BigDecimal totalRevenue() {
        return billRepository.findAll()
                .stream()
                .map(Bill::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private String generateBillNumber() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String random = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        return "BILL-" + timestamp + "-" + random;
    }
}
