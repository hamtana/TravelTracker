package com.CCDHB.NTA.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "BookedSupportPerson")
public class BookedSupportPersonEntity {

    @EmbeddedId
    private BookedSupportPersonKey id;

    @ManyToOne
    @MapsId("bookingId") // 👈 Tells Hibernate this FK is part of the composite key
    @JoinColumn(name = "booking_id")
    private BookingEntity booking;

    @ManyToOne
    @MapsId("supportPersonId") // 👈 Same idea for the other FK
    @JoinColumn(name = "support_person_id")
    private SupportPersonEntity supportPerson;

    public BookedSupportPersonKey getId() {
        return id;
    }

    public void setId(BookedSupportPersonKey id) {
        this.id = id;
    }

    public BookingEntity getBooking() {
        return booking;
    }

    public void setBooking(BookingEntity booking) {
        this.booking = booking;
    }

    public SupportPersonEntity getSupportPerson() {
        return supportPerson;
    }

    public void setSupportPerson(SupportPersonEntity supportPerson) {
        this.supportPerson = supportPerson;
    }
}
