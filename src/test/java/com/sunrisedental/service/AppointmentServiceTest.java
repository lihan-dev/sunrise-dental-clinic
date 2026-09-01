package com.sunrisedental.service;

import com.sunrisedental.dto.AppointmentForm;
import com.sunrisedental.exception.BusinessRuleException;
import com.sunrisedental.model.Dentist;
import com.sunrisedental.model.Treatment;
import com.sunrisedental.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {

    @Mock AppointmentRepository appointmentRepository;
    @Mock PatientRepository patientRepository;
    @Mock DentistRepository dentistRepository;
    @Mock TreatmentRepository treatmentRepository;
    @Mock AppointmentNumberGenerator numberGenerator;

    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        appointmentService = new AppointmentService(
                appointmentRepository,
                patientRepository,
                dentistRepository,
                treatmentRepository,
                numberGenerator);
    }

    @Test
    void shouldRejectDoubleBookingForSameDentistDateAndTime() {
        Dentist dentist = new Dentist("Dr Test", "General");
        Treatment treatment = new Treatment("Filling", new BigDecimal("6500.00"));
        AppointmentForm form = validForm();

        when(dentistRepository.findById(1L)).thenReturn(Optional.of(dentist));
        when(treatmentRepository.findById(1L)).thenReturn(Optional.of(treatment));
        when(appointmentRepository.existsByDentistAndAppointmentDateAndAppointmentTime(
                eq(dentist), any(LocalDate.class), any(LocalTime.class))).thenReturn(true);

        assertThrows(BusinessRuleException.class, () -> appointmentService.create(form));
        verify(patientRepository, never()).save(any());
        verify(appointmentRepository, never()).save(any());
    }

    private AppointmentForm validForm() {
        AppointmentForm form = new AppointmentForm();
        form.setPatientName("Test Patient");
        form.setAddress("10 Test Road, Colombo");
        form.setContactNumber("0771234567");
        form.setDentistId(1L);
        form.setTreatmentId(1L);
        form.setAppointmentDate(LocalDate.now().plusDays(1));
        form.setAppointmentTime(LocalTime.of(10, 0));
        return form;
    }
}
