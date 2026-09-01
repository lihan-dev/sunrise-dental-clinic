package com.sunrisedental.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "appointments",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_appointment_number", columnNames = "appointment_number"),
           @UniqueConstraint(name = "uk_dentist_slot", columnNames = {"dentist_id", "appointment_date", "appointment_time"})
       })
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "appointment_number", nullable = false, length = 40)
    private String appointmentNumber;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "dentist_id", nullable = false)
    private Dentist dentist;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "treatment_id", nullable = false)
    private Treatment treatment;

    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Column(name = "appointment_time", nullable = false)
    private LocalTime appointmentTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Appointment() {}

    public Long getId() { return id; }
    public String getAppointmentNumber() { return appointmentNumber; }
    public void setAppointmentNumber(String appointmentNumber) { this.appointmentNumber = appointmentNumber; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public Dentist getDentist() { return dentist; }
    public void setDentist(Dentist dentist) { this.dentist = dentist; }
    public Treatment getTreatment() { return treatment; }
    public void setTreatment(Treatment treatment) { this.treatment = treatment; }
    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }
    public LocalTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; }
    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
