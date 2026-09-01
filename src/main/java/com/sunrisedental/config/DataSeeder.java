package com.sunrisedental.config;

import com.sunrisedental.model.*;
import com.sunrisedental.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(
            UserAccountRepository userRepository,
            DentistRepository dentistRepository,
            TreatmentRepository treatmentRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                userRepository.save(new UserAccount(
                        "admin",
                        passwordEncoder.encode("Admin@123"),
                        Role.ADMIN));
            }

            if (userRepository.findByUsername("staff").isEmpty()) {
                userRepository.save(new UserAccount(
                        "staff",
                        passwordEncoder.encode("Staff@123"),
                        Role.STAFF));
            }

            if (dentistRepository.count() == 0) {
                dentistRepository.save(new Dentist("Dr. Nadeesha Perera", "General Dentistry"));
                dentistRepository.save(new Dentist("Dr. Kavindu Silva", "Orthodontics"));
                dentistRepository.save(new Dentist("Dr. Amaya Fernando", "Oral Surgery"));
            }

            if (treatmentRepository.count() == 0) {
                treatmentRepository.save(new Treatment("Dental Check-up", new BigDecimal("2500.00")));
                treatmentRepository.save(new Treatment("Teeth Cleaning", new BigDecimal("4500.00")));
                treatmentRepository.save(new Treatment("Filling", new BigDecimal("6500.00")));
                treatmentRepository.save(new Treatment("Root Canal", new BigDecimal("18000.00")));
                treatmentRepository.save(new Treatment("Tooth Extraction", new BigDecimal("8000.00")));
                treatmentRepository.save(new Treatment("Braces Consultation", new BigDecimal("5000.00")));
            }
        };
    }
}
