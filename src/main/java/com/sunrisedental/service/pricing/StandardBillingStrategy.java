package com.sunrisedental.service.pricing;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class StandardBillingStrategy implements BillingStrategy {

    @Override
    public BigDecimal calculateTotal(BigDecimal treatmentFee, BigDecimal consultationFee) {
        if (treatmentFee == null || consultationFee == null) {
            throw new IllegalArgumentException("Fees cannot be null");
        }
        if (treatmentFee.signum() < 0 || consultationFee.signum() < 0) {
            throw new IllegalArgumentException("Fees cannot be negative");
        }
        return treatmentFee.add(consultationFee);
    }
}
