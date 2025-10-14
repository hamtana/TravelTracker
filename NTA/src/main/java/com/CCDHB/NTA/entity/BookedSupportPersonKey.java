package com.CCDHB.NTA.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookedSupportPersonKey implements Serializable {

    @Column(name = "booking_id")
    private Long bookingId;

    @Column(name = "support_person_id")
    private Long supportPersonId;

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getSupportPersonId() {
        return supportPersonId;
    }

    public void setSupportPersonId(Long supportPersonId) {
        this.supportPersonId = supportPersonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookedSupportPersonKey that = (BookedSupportPersonKey) o;
        return Objects.equals(bookingId, that.bookingId)
                && Objects.equals(supportPersonId, that.supportPersonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, supportPersonId);
    }
}
