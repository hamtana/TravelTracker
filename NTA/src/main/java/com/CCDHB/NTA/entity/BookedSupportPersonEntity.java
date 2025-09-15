package com.CCDHB.NTA.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "BookedSupportPerson")
public class BookedSupportPersonEntity {

    @EmbeddedId
    private BookedSupportPersonKey id;

    @ManyToOne
    @JoinColumn(name = "bookingId", nullable = false)
    private BookingEntity booking;

    @ManyToOne
    @JoinColumn(name = "supportPersonId", nullable = false)
    private SupportPersonEntity supportPerson;

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
