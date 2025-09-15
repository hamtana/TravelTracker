package com.CCDHB.NTA.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite key class for BookedSupportPersonEntity.
 * Provides equals and hashCode implementations, allows us to declare a composite primary key.
 */
@Embeddable
public class BookedSupportPersonKey implements Serializable{

    @Column(name = "bookingId", insertable = false, updatable = false)
    private Long bookingId;

    @Column(name= "supportPersonId", insertable = false, updatable = false)
    private Long supportPersonId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookedSupportPersonKey that = (BookedSupportPersonKey) o;
        return Objects.equals(bookingId, that.bookingId) && Objects.equals(supportPersonId, that.supportPersonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, supportPersonId);
    }
}