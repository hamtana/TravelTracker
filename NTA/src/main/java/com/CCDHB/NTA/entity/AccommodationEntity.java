package com.CCDHB.NTA.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Accommodation")
public class AccommodationEntity {

    @Id
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "name", nullable = false)
    private String name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
