package com.CCDHB.api;

import com.CCDHB.model.Patient;
import com.CCDHB.model.Booking;
import com.CCDHB.model.SupportPerson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.*;

public class BookingController implements BookingsApi {

    private final Map<Integer, Booking> bookings = new HashMap<>();

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return BookingsApi.super.getRequest();
    }

    @Override
    public ResponseEntity<Booking> addBooking(Booking booking) {
        if(bookings.containsKey(booking.getId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        bookings.put(booking.getId(), booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @Override
    public ResponseEntity<Void> deleteBookingById(Integer id) {
        // Check if Booking exists
        if(bookings.containsKey(id)){
            bookings.remove(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<Booking> getBookingById(Integer id) {
        Booking booking = bookings.get(id);
        if(booking != null){
            return ResponseEntity.ok(booking);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<List<Booking>> getBookings() {
        if(bookings.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(new ArrayList<>(bookings.values()));
    }

    @Override
    public ResponseEntity<Booking> updateBookingById(Integer id, Booking booking) {
        if(!bookings.containsKey(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        booking.setId(id); // keep ID consistent
        bookings.put(id, booking);
        return ResponseEntity.ok(booking);

    }
}
