package com.sunrisedental.controller;

import com.sunrisedental.model.Bill;
import com.sunrisedental.service.BillingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bills")
public class BillingRestController {

    private final BillingService billingService;

    public BillingRestController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping("/{appointmentNumber}")
    public Bill generate(@PathVariable String appointmentNumber) {
        return billingService.generateForAppointment(appointmentNumber);
    }

    @GetMapping("/{billNumber}")
    public Bill get(@PathVariable String billNumber) {
        return billingService.findByBillNumber(billNumber);
    }
}
