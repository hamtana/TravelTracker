package com.CCDHB.api;

import com.CCDHB.NTA.entity.BookingEntity;
import com.CCDHB.NTA.entity.PatientEntity;
import com.CCDHB.NTA.mapper.BookingMapper;
import com.CCDHB.NTA.mapper.PatientMapper;
import com.CCDHB.NTA.repository.BookingRepository;
import com.CCDHB.NTA.repository.PatientRepository;
import com.CCDHB.model.Patient;
import com.CCDHB.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@CrossOrigin(origins = "*") // Allow requests from any origin
public class PatientController implements PatientsApi {

    // Follows the API Specification PatientApi interface
    // In-memory store of patients by NHI
    // private final HashMap<String, Patient> patients = new HashMap<>();

    // Database version
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private BookingMapper bookingMapper;
    @Autowired
    private BookingRepository bookingRepository;


    /** Add a new patient.
     * @param patient  (required)
     * @return HTTP status code indicating the result of the operation
     */
    @Override
    public ResponseEntity<Patient> addPatient(Patient patient) {
        if (patientRepository.existsById(patient.getNhi())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Conflict
        }
        patientRepository.save(patientMapper.toEntity(patient));
        return ResponseEntity.status(HttpStatus.CREATED).build(); // Created
    }

    /**
     * Add a booking for a specific patient using their NHI.
     * @param nhi  (required)
     * @param booking  (required)
     * @return HTTP status code indicating the result of the operation
     */
    @Override
    public ResponseEntity<Void> addPatientBooking(String nhi, Booking booking) {
        if (!patientRepository.existsById(nhi)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Not Found
        }
        // Logic to add booking to patient would go here
       bookingRepository.save(bookingMapper.toEntity(booking));
        return ResponseEntity.status(HttpStatus.CREATED).build(); // Created
    }

    /** Delete a patient using their NHI.
     * @param nhi  (required)
     * @return HTTP status code indicating the result of the operation
     */
    @Override
    public ResponseEntity<Void> deletePatientById(String nhi) {
        if(patientRepository.existsById(nhi)){
            patientRepository.deleteById(nhi);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // No Content
        }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Not Found
    }

    /**
     * Get all Booking for a specific patient using their NHI.
     * @param nhi  (required)
     * @return List of Bookings for the patient
     */
    @Override
    public ResponseEntity<List<Booking>> getPatientBookings(String nhi) {
        if (!patientRepository.existsById(nhi)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Not Found
        }

        List<BookingEntity> bookingEntities = bookingRepository.findByPatientNhi(nhi);
        List <Booking> bookings = bookingEntities.stream()
                .map(bookingMapper::toDto)
                .toList();

        return ResponseEntity.ok(bookings);

    }

    /** Get a patient using their NHI.
     * @param nhi  (required)
     * @return The patient details if found, otherwise a 404 Not Found status
     */
    @Override
    public ResponseEntity<Patient> getPatientById(String nhi) {
        Optional<PatientEntity> patientEntity = patientRepository.findById(nhi);
        if(patientEntity.isPresent()){
            return ResponseEntity.ok(patientMapper.toDto(patientEntity.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Not Found
    }

    /** Get all patients.
     *
     * @return List of all patients
     */
    @Override
    public ResponseEntity<List<Patient>> getPatients() {
        List<PatientEntity> patientEntities = patientRepository.findAll();
        if (patientEntities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<Patient> patients = patientEntities.stream()
                .map(patientMapper::toDto)
                .toList();
        return ResponseEntity.ok(patients);
    }

    /** Update a patient using their NHI.
     * @param nhi  (required)
     * @param patient  (required)
     * @return The updated patient details if found, otherwise a 404 Not Found status
     */
    @Override
    public ResponseEntity<Patient> updatePatientById(String nhi, Patient patient) {
        if (!patientRepository.existsById(nhi)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Not Found
        }
        patient.setNhi(nhi); // keep NHI consistent
        patientRepository.save(patientMapper.toEntity(patient));
        return ResponseEntity.ok(patient); // OK

    }
}
