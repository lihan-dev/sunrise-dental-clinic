package com.sunrisedental.service;

import com.sunrisedental.service.pricing.StandardBillingStrategy;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class StandardBillingStrategyTest {

    private final StandardBillingStrategy strategy = new StandardBillingStrategy();

    @Test
    void shouldAddTreatmentAndConsultationFees() {
        BigDecimal total = strategy.calculateTotal(
                new BigDecimal("6500.00"),
                new BigDecimal("1500.00"));

        assertEquals(new BigDecimal("8000.00"), total);
    }

    @Test
    void shouldRejectNegativeFees() {
        assertThrows(IllegalArgumentException.class, () ->
                strategy.calculateTotal(
                        new BigDecimal("-1.00"),
                        new BigDecimal("1500.00")));
    }
}
