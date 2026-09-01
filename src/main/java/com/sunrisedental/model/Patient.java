package com.sunrisedental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "patients", uniqueConstraints = {
        @UniqueConstraint(name = "uk_patient_contact", columnNames = "contact_number")
})
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(name = "contact_number", nullable = false, length = 20)
    private String contactNumber;

    public Patient() {}

    public Patient(String name, String address, String contactNumber) {
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
}
