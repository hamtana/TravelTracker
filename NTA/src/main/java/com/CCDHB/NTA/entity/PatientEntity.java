package com.CCDHB.NTA.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Patient")
public class PatientEntity {
    @Id
    @Column(name = "nhi", nullable = false, unique = true, length = 7)
    private String nhi;

    @Column(name = "ntaNumber")
    private String ntaNumber;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "surname", nullable = false)
    private String surname;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingEntity> bookings;

    // Getters and Setters

    public String getNhi() {
        return nhi;
    }

    public void setNhi(String nhi) {
        this.nhi = nhi;
    }

    public String getNtaNumber() {
        return ntaNumber;
    }

    public void setNtaNumber(String ntaNumber) {
        this.ntaNumber = ntaNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<BookingEntity> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingEntity> bookings) {
        this.bookings = bookings;
    }
}

