package com.sunrisedental.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "treatments")
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "treatment_fee", nullable = false, precision = 10, scale = 2)
    private BigDecimal treatmentFee;

    @Column(nullable = false)
    private boolean active = true;

    public Treatment() {}

    public Treatment(String name, BigDecimal treatmentFee) {
        this.name = name;
        this.treatmentFee = treatmentFee;
        this.active = true;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getTreatmentFee() { return treatmentFee; }
    public void setTreatmentFee(BigDecimal treatmentFee) { this.treatmentFee = treatmentFee; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
