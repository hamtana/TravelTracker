package com.CCDHB.NTA.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Note")
public class NoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="message", nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "bookingId")
    private BookingEntity booking;

    @ManyToOne
    @JoinColumn(name = "patientNhi")
    private PatientEntity patient;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
