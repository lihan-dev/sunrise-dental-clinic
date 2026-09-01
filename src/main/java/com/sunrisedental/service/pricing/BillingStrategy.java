package com.sunrisedental.service.pricing;

import java.math.BigDecimal;

public interface BillingStrategy {
    BigDecimal calculateTotal(BigDecimal treatmentFee, BigDecimal consultationFee);
}
