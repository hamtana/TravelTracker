package com.CCDHB.api;

import com.CCDHB.NTA.entity.BookingEntity;
import com.CCDHB.NTA.entity.PatientEntity;
import com.CCDHB.NTA.mapper.BookingMapper;
import com.CCDHB.NTA.mapper.PatientMapper;
import com.CCDHB.NTA.repository.BookingRepository;
import com.CCDHB.NTA.repository.PatientRepository;
import com.CCDHB.model.Note;
import com.CCDHB.model.Patient;
import com.CCDHB.model.Booking;
import com.CCDHB.model.SupportPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.*;

@RestController
@CrossOrigin(origins = "*") // Allow requests from any origin
public class BookingController implements BookingsApi {

    //private final Map<Integer, Booking> bookings = new HashMap<>();

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingMapper bookingMapper;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientMapper patientMapper;

    @Override
    public ResponseEntity<Void> addBookingNote(String id, Note note) {
        return BookingsApi.super.addBookingNote(id, note);
    }

    @Override
    public ResponseEntity<Void> addPatientBooking(String nhi, Booking booking) {
        BookingEntity bookingEntity = bookingMapper.toEntity(booking);
        // Check if patient with given NHI exists
        if (!patientRepository.existsById(nhi)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PatientEntity patientEntity = patientRepository.getReferenceById(nhi);
        bookingEntity.setPatient(patientEntity);
        bookingRepository.save(bookingEntity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteBookingById(Integer id, String nhi) {
       // Check if booking exists and belongs to patient with given NHI
        Optional<BookingEntity> bookingEntityOpt = bookingRepository.findById(id);
        if (bookingEntityOpt.isPresent() && bookingEntityOpt.get().getPatient().getNhi().equals(nhi)) {
            bookingRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Booking> getBookingById(Integer id, String nhi) {
       // Get booking by nhi and id
        Optional<BookingEntity> bookingEntityOpt = bookingRepository.findById(id);
        if (bookingEntityOpt.isPresent() && bookingEntityOpt.get().getPatient().getNhi().equals(nhi)) {
            Booking booking = bookingMapper.toDto(bookingEntityOpt.get());
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<Note>> getBookingNotes(String id) {
        return BookingsApi.super.getBookingNotes(id);
    }

    @Override
    public ResponseEntity<List<Booking>> getPatientBookings(String nhi) {
        List<BookingEntity> bookingEntities = bookingRepository.findByPatientNhi(nhi);
        // need to convert List<BookingEntity> to List<Booking>
        List<Booking> bookings = new ArrayList<>();
        for (BookingEntity bookingEntity : bookingEntities) {
            bookings.add(bookingMapper.toDto(bookingEntity));
        }
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Booking> updateBookingById(Integer id, String nhi, Booking booking) {
        // Check if booking exists and belongs to patient with given NHI
        Optional<BookingEntity> bookingEntityOpt = bookingRepository.findById(id);
        if (bookingEntityOpt.isPresent() && bookingEntityOpt.get().getPatient().getNhi().equals(nhi)) {
            BookingEntity bookingEntityToUpdate = bookingEntityOpt.get();
            // Set the ID to ensure consistency and update the entity
            booking.setId(id);
            BookingEntity updatedEntity = bookingMapper.toEntity(booking);
            // Preserve the patient association
            updatedEntity.setPatient(bookingEntityToUpdate.getPatient());
            // Save updated entity
            bookingRepository.save(bookingEntityToUpdate);
            return new ResponseEntity<>(bookingMapper.toDto(bookingEntityToUpdate), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
