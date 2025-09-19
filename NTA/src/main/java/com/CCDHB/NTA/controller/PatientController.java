package com.CCDHB.api;

import com.CCDHB.NTA.entity.BookingEntity;
import com.CCDHB.NTA.entity.NoteEntity;
import com.CCDHB.NTA.entity.PatientEntity;
import com.CCDHB.NTA.entity.PatientServiceProviderEntity;
import com.CCDHB.NTA.mapper.BookingMapper;
import com.CCDHB.NTA.mapper.PatientMapper;
import com.CCDHB.NTA.mapper.NoteMapper;
import com.CCDHB.NTA.mapper.PatientServiceProviderMapper;
import com.CCDHB.NTA.repository.BookingRepository;
import com.CCDHB.NTA.repository.PatientRepository;
import com.CCDHB.model.Note;
import com.CCDHB.model.Patient;
import com.CCDHB.model.Booking;
import com.CCDHB.model.PatientServiceProvider;
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
    @Autowired
    private NoteMapper noteMapper;
    @Autowired
    private PatientServiceProviderMapper patientServiceProviderMapper;


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

    /** Add a Note to a specific Patient using their NHI.
     * @param nhi  (required)
     * @param note  (required)
     * @return HTTP status code indicating the result of the operation
     */
    @Override
    public ResponseEntity<Void> addPatientNote(String nhi, Note note) {
        if(!patientRepository.existsById(nhi)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Not Found
        }
        // Retrieve the patient entity
        PatientEntity patientEntity = patientRepository.findById(nhi).get();

        // Create a new NoteEntity and set its properties
        NoteEntity noteEntity = noteMapper.toEntity(note);
        patientEntity.getNotes().add(noteEntity);

        // send the updated patient entity to the repository to save
        patientRepository.save(patientEntity);

        return ResponseEntity.status(HttpStatus.CREATED).build(); // Created

    }

    /**
     * Add a Service Provider to a specific Patient using their NHI.
     * @param nhi  (required)
     * @param patientServiceProvider  (required)
     * @return HTTP status code indicating the result of the operation
     */
    @Override
    public ResponseEntity<Void> addPatientServiceProvider(String nhi, PatientServiceProvider patientServiceProvider) {
        if(!patientRepository.existsById(nhi)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Not Found
        }
        PatientServiceProviderEntity serviceProviderEntity = patientServiceProviderMapper.toEntity(patientServiceProvider);
        PatientEntity patientEntity = patientRepository.findById(nhi).get();

        patientEntity.getPatientServiceProviders().add(serviceProviderEntity);
        patientRepository.save(patientEntity);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // Created
    }

    /**
     * Retrieve all Notes associated with a specific patient
     * @param nhi  (required)
     * @return List of Notes for the patient
     */
    @Override
    public ResponseEntity<List<Note>> getPatientNotes(String nhi) {
        if(!patientRepository.existsById(nhi)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Not Found
        }
        PatientEntity patientEntity = patientRepository.findById(nhi).get();
        List<Note> notes = patientEntity.getNotes().stream()
                .map(noteMapper::toDto)
                .toList();
        return ResponseEntity.ok(notes);
    }

    /**
     * Retrieve a specific Service Provider associated with a specific Patient
     * @param nhi  (required)
     * @param id  (required)
     * @return
     */
    @Override
    public ResponseEntity<PatientServiceProvider> getPatientServiceProvider(String nhi, String id) {
        if(!patientRepository.existsById(nhi)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Not Found
        }
        PatientEntity patientEntity = patientRepository.findById(nhi).get();

        Optional<PatientServiceProviderEntity> serviceProviderEntity = patientEntity.getPatientServiceProviders().stream()
                .filter(sp -> sp.getId().equals(id))
                .findFirst();
        // Not Found
        return serviceProviderEntity.map(patientServiceProviderEntity -> ResponseEntity.ok(patientServiceProviderMapper.toDto(patientServiceProviderEntity))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Retrieve all the Service Providers associated with a specific Patient
     * @param nhi  (required)
     * @return List of Service Providers for the patient
     */
    @Override
    public ResponseEntity<List<PatientServiceProvider>> getPatientServiceProviders(String nhi) {
        if(!patientRepository.existsById(nhi)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Not Found
        }
        PatientEntity patientEntity = patientRepository.findById(nhi).get();
        List<PatientServiceProvider> serviceProviders = patientEntity.getPatientServiceProviders().stream()
                .map(patientServiceProviderMapper::toDto)
                .toList();
        return ResponseEntity.ok(serviceProviders);
    }

    /**
     * Update a specific Service Provider associated with a specific Patient
     * @param nhi  (required)
     * @param id  (required)
     * @param patientServiceProvider  (required)
     * @return HTTP status code indicating the result of the operation
     */
    @Override
    public ResponseEntity<Void> updatePatientServiceProvider(String nhi, String id, PatientServiceProvider patientServiceProvider) {
        // Fetch patient
        Optional<PatientEntity> patientOpt = patientRepository.findById(nhi);
        if (patientOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Patient not found
        }
        PatientEntity patientEntity = patientOpt.get();

        // Find the service provider
        Optional<PatientServiceProviderEntity> serviceProviderEntityOpt = patientEntity.getPatientServiceProviders()
                .stream()
                .filter(sp -> sp.getId().equals(id))
                .findFirst();

        if (serviceProviderEntityOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Service provider not found
        }

        PatientServiceProviderEntity serviceProviderEntity = serviceProviderEntityOpt.get();

        // Update fields (expand if you have more)
        serviceProviderEntity.setFrequency(patientServiceProvider.getFrequency());
        serviceProviderEntity.setNotes(
                patientServiceProvider.getNotes().stream()
                        .map(noteMapper::toEntity)
                        .toList()
        );

        // If PatientServiceProviderEntity is managed by cascade on PatientEntity
        patientRepository.save(patientEntity);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Success, no content
    }


}
