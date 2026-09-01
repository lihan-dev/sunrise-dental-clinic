package com.sunrisedental.service;

import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class AppointmentNumberGenerator {

    public String generate() {
        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String random = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "APT-" + date + "-" + random;
    }
}
