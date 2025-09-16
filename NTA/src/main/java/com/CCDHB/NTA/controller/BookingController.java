package com.CCDHB.api;

import com.CCDHB.NTA.entity.BookingEntity;
import com.CCDHB.NTA.mapper.BookingMapper;
import com.CCDHB.NTA.repository.BookingRepository;
import com.CCDHB.model.Patient;
import com.CCDHB.model.Booking;
import com.CCDHB.model.SupportPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.*;

@RestController
public class BookingController implements BookingsApi {

    //private final Map<Integer, Booking> bookings = new HashMap<>();

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingMapper bookingMapper;

    /**
     * Add a new booking.
     * @param booking  (required)
     * @return HTTP status code indicating the result of the operation.
     */
    @Override
    public ResponseEntity<Booking> addBooking(Booking booking) {
        if(bookingRepository.existsById(booking.getId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        bookingRepository.save(bookingMapper.toEntity(booking));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Delete a booking by its ID.
     * @param id  (required)
     * @return HTTP status code indicating the result of the operation.
     */
    @Override
    public ResponseEntity<Void> deleteBookingById(Integer id) {
        if(bookingRepository.existsById(id)){
            bookingRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /** Get a booking by its ID.
     * @param id  (required)
     * @return The booking if found, or HTTP status code indicating the result of the operation.
     */
    @Override
    public ResponseEntity<Booking> getBookingById(Integer id) {
        Optional<BookingEntity> bookingEntity = bookingRepository.findById(id);
        return bookingEntity.map(entity -> ResponseEntity.ok(bookingMapper.toDto(entity))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /** Get all bookings.
     * @return List of all bookings, or HTTP status code indicating the result of the operation.
     */
    @Override
    public ResponseEntity<List<Booking>> getBookings() {
        List<BookingEntity> bookingEntities = bookingRepository.findAll();
        if(bookingEntities.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<Booking> bookings = bookingEntities.stream().map(bookingMapper::toDto).toList();
        return ResponseEntity.ok(bookings);
    }

    /** Update a booking by its ID.
     * @param id  (required)
     * @param booking  (required)
     * @return The updated booking if found, or HTTP status code indicating the result of the operation.
     */
    @Override
    public ResponseEntity<Booking> updateBookingById(Integer id, Booking booking) {
        if(!bookingRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        booking.setId(id);
        bookingRepository.save(bookingMapper.toEntity(booking));
        return ResponseEntity.ok(booking);

    }
}
