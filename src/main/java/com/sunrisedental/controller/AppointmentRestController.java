package com.sunrisedental.controller;

import com.sunrisedental.dto.AppointmentForm;
import com.sunrisedental.model.Appointment;
import com.sunrisedental.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentRestController {

    private final AppointmentService appointmentService;

    public AppointmentRestController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/{appointmentNumber}")
    public Appointment get(@PathVariable String appointmentNumber) {
        return appointmentService.getByNumber(appointmentNumber);
    }

    @GetMapping
    public List<Appointment> byDate(@RequestParam LocalDate date) {
        return appointmentService.getByDate(date);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Appointment create(@Valid @RequestBody AppointmentForm form) {
        return appointmentService.create(form);
    }
}
