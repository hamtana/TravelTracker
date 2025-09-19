package com.CCDHB.NTA.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Note")
public class NoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name="message", nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "booking")
    private BookingEntity booking;

    @ManyToOne
    @JoinColumn(name = "patient")
    private PatientEntity patient;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
