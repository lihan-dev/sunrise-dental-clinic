package com.sunrisedental.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bills", uniqueConstraints = {
        @UniqueConstraint(name = "uk_bill_number", columnNames = "bill_number"),
        @UniqueConstraint(name = "uk_bill_appointment", columnNames = "appointment_id")
})
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bill_number", nullable = false, length = 40)
    private String billNumber;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @Column(name = "consultation_fee", nullable = false, precision = 10, scale = 2)
    private BigDecimal consultationFee;

    @Column(name = "treatment_fee", nullable = false, precision = 10, scale = 2)
    private BigDecimal treatmentFee;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "generated_at", nullable = false)
    private LocalDateTime generatedAt = LocalDateTime.now();

    public Bill() {}

    public Long getId() { return id; }
    public String getBillNumber() { return billNumber; }
    public void setBillNumber(String billNumber) { this.billNumber = billNumber; }
    public Appointment getAppointment() { return appointment; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }
    public BigDecimal getConsultationFee() { return consultationFee; }
    public void setConsultationFee(BigDecimal consultationFee) { this.consultationFee = consultationFee; }
    public BigDecimal getTreatmentFee() { return treatmentFee; }
    public void setTreatmentFee(BigDecimal treatmentFee) { this.treatmentFee = treatmentFee; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
}
