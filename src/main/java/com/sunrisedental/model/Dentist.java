package com.sunrisedental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dentists")
public class Dentist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String specialization;

    @Column(nullable = false)
    private boolean active = true;

    public Dentist() {}

    public Dentist(String name, String specialization) {
        this.name = name;
        this.specialization = specialization;
        this.active = true;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
