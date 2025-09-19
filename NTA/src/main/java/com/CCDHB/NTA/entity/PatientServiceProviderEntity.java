package com.CCDHB.NTA.entity;

import com.CCDHB.model.ServiceProvider;
import jakarta.persistence.*;

import java.util.List;


@Entity
public class PatientServiceProviderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @JoinColumn(name= "serviceProviderId", nullable = false)
    private ServiceProvider serviceProvider;

    @Column(name = "frequency", nullable = false)
    private String frequency;

    @OneToMany(mappedBy = "patientServiceProvider", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoteEntity> notes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public List<NoteEntity> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteEntity> notes) {
        this.notes = notes;
    }
}
