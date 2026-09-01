package com.sunrisedental.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppointmentNumberGeneratorTest {

    @Test
    void shouldCreateAppointmentNumberWithExpectedPrefix() {
        AppointmentNumberGenerator generator = new AppointmentNumberGenerator();
        String number = generator.generate();

        assertNotNull(number);
        assertTrue(number.startsWith("APT-"));
        assertTrue(number.length() >= 18);
    }

    @Test
    void shouldGenerateDifferentNumbers() {
        AppointmentNumberGenerator generator = new AppointmentNumberGenerator();
        assertNotEquals(generator.generate(), generator.generate());
    }
}
