package com.sunrisedental.dto;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentForm {

    @NotBlank(message = "Patient name is required")
    @Size(min = 2, max = 120, message = "Patient name must be 2-120 characters")
    private String patientName;

    @NotBlank(message = "Address is required")
    @Size(min = 5, max = 255, message = "Address must be 5-255 characters")
    private String address;

    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^[0-9+()\\- ]{7,20}$", message = "Enter a valid contact number")
    private String contactNumber;

    @NotNull(message = "Dentist is required")
    private Long dentistId;

    @NotNull(message = "Treatment is required")
    private Long treatmentId;

    @NotNull(message = "Appointment date is required")
    @FutureOrPresent(message = "Appointment date cannot be in the past")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate appointmentDate;

    @NotNull(message = "Appointment time is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime appointmentTime;

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public Long getDentistId() { return dentistId; }
    public void setDentistId(Long dentistId) { this.dentistId = dentistId; }
    public Long getTreatmentId() { return treatmentId; }
    public void setTreatmentId(Long treatmentId) { this.treatmentId = treatmentId; }
    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }
    public LocalTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; }
}
