package com.CCDHB.NTA.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Patient")
public class PatientEntity {
    @Id
    @Column(name = "nhi", unique = true, length = 7)
    private String nhi;

    @Column(name = "nta_number", unique = true, length = 7)
    private String ntaNumber;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "surname", nullable = false)
    private String surname;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoteEntity> notes;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatientServiceProviderEntity> patientServiceProviders;

    public List<PatientServiceProviderEntity> getPatientServiceProviders() {
        return patientServiceProviders;
    }

    public void setPatientServiceProviders(List<PatientServiceProviderEntity> patientServiceProviders) {
        this.patientServiceProviders = patientServiceProviders;
    }

    // Getters and Setters

    public List<NoteEntity> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteEntity> notes) {
        this.notes = notes;
    }

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

}

