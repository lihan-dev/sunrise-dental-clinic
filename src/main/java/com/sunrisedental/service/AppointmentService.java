package com.sunrisedental.service;

import com.sunrisedental.dto.AppointmentForm;
import com.sunrisedental.exception.BusinessRuleException;
import com.sunrisedental.exception.ResourceNotFoundException;
import com.sunrisedental.model.*;
import com.sunrisedental.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DentistRepository dentistRepository;
    private final TreatmentRepository treatmentRepository;
    private final AppointmentNumberGenerator numberGenerator;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            PatientRepository patientRepository,
            DentistRepository dentistRepository,
            TreatmentRepository treatmentRepository,
            AppointmentNumberGenerator numberGenerator) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.dentistRepository = dentistRepository;
        this.treatmentRepository = treatmentRepository;
        this.numberGenerator = numberGenerator;
    }

    public Appointment create(AppointmentForm form) {
        Dentist dentist = dentistRepository.findById(form.getDentistId())
                .orElseThrow(() -> new ResourceNotFoundException("Selected dentist was not found"));

        Treatment treatment = treatmentRepository.findById(form.getTreatmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Selected treatment was not found"));

        boolean occupied = appointmentRepository.existsByDentistAndAppointmentDateAndAppointmentTime(
                dentist, form.getAppointmentDate(), form.getAppointmentTime());

        if (occupied) {
            throw new BusinessRuleException("This dentist already has an appointment at that date and time.");
        }

        String contact = form.getContactNumber().trim();

        Patient patient = patientRepository.findByContactNumber(contact)
                .map(existing -> {
                    existing.setName(form.getPatientName().trim());
                    existing.setAddress(form.getAddress().trim());
                    return patientRepository.save(existing);
                })
                .orElseGet(() -> patientRepository.save(new Patient(
                        form.getPatientName().trim(),
                        form.getAddress().trim(),
                        contact)));

        Appointment appointment = new Appointment();
        appointment.setAppointmentNumber(numberGenerator.generate());
        appointment.setPatient(patient);
        appointment.setDentist(dentist);
        appointment.setTreatment(treatment);
        appointment.setAppointmentDate(form.getAppointmentDate());
        appointment.setAppointmentTime(form.getAppointmentTime());
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        return appointmentRepository.save(appointment);
    }

    @Transactional(readOnly = true)
    public Appointment getByNumber(String appointmentNumber) {
        return appointmentRepository.findByAppointmentNumber(appointmentNumber.trim())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No appointment found for number: " + appointmentNumber));
    }

    @Transactional(readOnly = true)
    public List<Appointment> getByDate(LocalDate date) {
        return appointmentRepository.findByAppointmentDateOrderByAppointmentTimeAsc(date);
    }

    @Transactional(readOnly = true)
    public List<Appointment> recent() {
        return appointmentRepository.findTop10ByOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public long countToday() {
        return appointmentRepository.countByAppointmentDate(LocalDate.now());
    }

    public Appointment updateStatus(String appointmentNumber, AppointmentStatus status) {
        Appointment appointment = getByNumber(appointmentNumber);
        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }
}
