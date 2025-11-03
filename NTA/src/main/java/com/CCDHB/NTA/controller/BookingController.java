package com.CCDHB.api;

import com.CCDHB.NTA.entity.BookingEntity;
import com.CCDHB.NTA.entity.NoteEntity;
import com.CCDHB.NTA.entity.PatientEntity;
import com.CCDHB.NTA.mapper.BookingMapper;
import com.CCDHB.NTA.mapper.NoteMapper;
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
import java.util.stream.Collectors;

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
    private NoteMapper noteMapper;

    @Autowired
    private PatientMapper patientMapper;

    @Override
    public ResponseEntity<Void> addBookingNote(String id, Note note) {
        BookingEntity bookingEntity = bookingRepository.findById(Integer.parseInt(id)).orElse(null);
        if (bookingEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            NoteEntity noteEntity = noteMapper.toEntity(note);
            noteEntity.setBooking(bookingEntity);
            if (bookingEntity.getNotes() == null) {
                bookingEntity.setNotes(new ArrayList<>());
            }
            bookingEntity.getNotes().add(noteEntity);
            bookingRepository.save(bookingEntity);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

    }

    @Override
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<BookingEntity> bookingEntities = bookingRepository.findAll();
        // need to convert List<BookingEntity> to List<Booking>
        List<Booking> bookings = new ArrayList<>();
        for (BookingEntity bookingEntity : bookingEntities) {
            bookings.add(bookingMapper.toDto(bookingEntity));
        }
        return new ResponseEntity<>(bookings, HttpStatus.OK);
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
        // Parse booking ID
        int bookingId;
        try {
            bookingId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Fetch booking from repository
        Optional<BookingEntity> bookingEntityOpt = bookingRepository.findById(bookingId);
        if (bookingEntityOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        BookingEntity bookingEntity = bookingEntityOpt.get();

        // Get notes from booking, handle null
        List<NoteEntity> noteEntities = bookingEntity.getNotes() != null
                ? bookingEntity.getNotes()
                : Collections.emptyList();

        // Map NoteEntity to Note DTO
        List<Note> notes = noteEntities.stream()
                .map(noteMapper::toDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(notes, HttpStatus.OK);
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
        Optional<BookingEntity> bookingEntityOpt = bookingRepository.findById(id);
        if (bookingEntityOpt.isPresent() && bookingEntityOpt.get().getPatient().getNhi().equals(nhi)) {
            BookingEntity bookingEntityToUpdate = bookingEntityOpt.get();

            // Preserve the patient association
            BookingEntity updatedEntity = bookingMapper.toEntity(booking);
            updatedEntity.setPatient(bookingEntityToUpdate.getPatient());

            // Save the updated booking entity
            bookingRepository.save(bookingEntityToUpdate);

            return new ResponseEntity<>(bookingMapper.toDto(bookingEntityToUpdate), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    }
