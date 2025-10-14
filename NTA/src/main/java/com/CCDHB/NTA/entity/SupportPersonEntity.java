package com.CCDHB.NTA.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "SupportPerson")
public class SupportPersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "covered_by_nta", nullable = false)
    private Boolean coveredByNta;

    @ManyToOne
    @JoinColumn(name = "patient_nhi", nullable = false)
    private PatientEntity patient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return surname;
    }

    public void setLastName(String lastName) {
        this.surname = lastName;
    }

    public Boolean getCoveredByNta() {
        return coveredByNta;
    }

    public void setCoveredByNta(Boolean coveredByNta) {
        this.coveredByNta = coveredByNta;
    }

    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }
}
