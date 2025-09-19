package com.CCDHB.NTA.entity;


import com.CCDHB.NTA.entity.ServiceProviderEntity;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "PatientServiceProvider")
public class PatientServiceProviderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @JoinColumn(name= "serviceProviderId", nullable = false)
    private ServiceProviderEntity serviceProvider;

    @Column(name = "frequency", nullable = false)
    private String frequency;

    @ManyToOne
    @JoinColumn(name = "patient", nullable = false)
    private PatientEntity patient;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ServiceProviderEntity getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProviderEntity serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }



}
