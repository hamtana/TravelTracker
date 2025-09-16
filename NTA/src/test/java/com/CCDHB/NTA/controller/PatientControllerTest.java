package com.CCDHB.NTA.controller;

import com.CCDHB.NTA.entity.BookingEntity;
import com.CCDHB.NTA.entity.PatientEntity;
import com.CCDHB.NTA.entity.ServiceProviderEntity;
import com.CCDHB.NTA.mapper.AccommodationMapper;
import com.CCDHB.NTA.mapper.BookingMapper;
import com.CCDHB.NTA.mapper.PatientMapper;
import com.CCDHB.NTA.mapper.ServiceProviderMapper;
import com.CCDHB.NTA.repository.AccommodationRepository;
import com.CCDHB.NTA.repository.BookingRepository;
import com.CCDHB.NTA.repository.PatientRepository;
import com.CCDHB.NTA.repository.ServiceProviderRepository;
import com.CCDHB.model.*;
import jakarta.transaction.Transactional;
import net.bytebuddy.pool.TypePool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class PatientControllerTest {

    // Using HashMap Local testing
   //  HashMap<String, Patient> patients = new HashMap<>();

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingMapper bookingMapper;

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Autowired
    private ServiceProviderMapper serviceProviderMapper;

    // For the purposes of this test, we are not using accommodation or adding Support Persons.

    Patient patient = new Patient();
    Patient patient2 = new Patient();
    Patient patient3 = new Patient();

    Booking booking1 = new Booking();
    ServiceProvider serviceProvider1 = new ServiceProvider();
    ServiceProviderEntity savedServiceProvider = new ServiceProviderEntity();

    @BeforeEach
    void setUp() {

        patient.setNhi("ABC1234");
        patient.setFirstName("John");
        patient.setSurname("Doe");
        patient.setNtaNumber("1234678");

        patient2.setNhi("XYZ5678");
        patient2.setFirstName("Jane");
        patient2.setSurname("Smith");
        patient2.setNtaNumber("8765432");

        patient3.setNhi("LMN8901");
        patient3.setFirstName("Alice");
        patient3.setSurname("Johnson");
        patient3.setNtaNumber("1122334");

        serviceProvider1.setName("Health Services Ltd");
        serviceProvider1.setAddress("123 Health St, Wellness City");

        // Save and reassign the managed entity
        savedServiceProvider = serviceProviderRepository.save(
                serviceProviderMapper.toEntity(serviceProvider1)
        );

        booking1.setDateOfDeparture(LocalDate.of(2025, 9, 15));
        booking1.setDateOfReturn(LocalDate.of(2025, 9, 20));
        booking1.setBookingStatus("Completed");
        booking1.setEstimatedCost(1500.0f);
        booking1.setEstimatedCostForPatient(0.0f);
        booking1.setBookingCreatedAt(OffsetDateTime.now());
        booking1.setServiceProvider(serviceProvider1);
        booking1.setDestination("Auckland");




    }

    @AfterEach
    void tearDown() {
        patientRepository.deleteAll();
    }

    @Test
    void addPatient() {

        //patient.setBookings(List.of(booking1));
        //booking1.setServiceProvider(serviceProviderMapper.toDto(savedServiceProvider));

        PatientEntity patientEntity = patientMapper.toEntity(patient);
        patientRepository.save(patientEntity);
        patientRepository.save(patientMapper.toEntity(patient2));

        List<PatientEntity> patientEntities = patientRepository.findAll();

        assertThat(patientEntities.size(), is(2));;
    }

    @Test
    void deletePatientById() {
        patientRepository.save(patientMapper.toEntity(patient));
        patientRepository.save(patientMapper.toEntity(patient2));

        assertThat(patientRepository.findAll().size(), is(2));

        patientRepository.deleteById(patient.getNhi());

        assertThat(patientRepository.findAll().size(), is(1));
        // Check that
        assertThat(patientRepository.findById(patient3.getNhi()), is(Optional.empty()));
    }

    @Test
    void getPatientById() {
        patientRepository.save(patientMapper.toEntity(patient));
        patientRepository.save(patientMapper.toEntity(patient2));

        Optional <PatientEntity> retrievedPatient = patientRepository.findById(patient.getNhi());
        assertThat(retrievedPatient.isPresent(), is(true));
        assertThat(retrievedPatient.get().getFirstName(), is("John"));
    }

    @Test
    void getPatients() {
        patientRepository.save(patientMapper.toEntity(patient));
        patientRepository.save(patientMapper.toEntity(patient2));
        patientRepository.save(patientMapper.toEntity(patient3));

        List<PatientEntity> patientEntities = patientRepository.findAll();
        assertThat(patientEntities.size(), is(3));
        assertThat(patientEntities, hasItem(
                allOf(
                        hasProperty("nhi", is("ABC1234")),
                        hasProperty("firstName", is("John")),
                        hasProperty("surname", is("Doe"))
                )
        ));
    }

    @Test
    void updatePatientById() {
        patientRepository.save(patientMapper.toEntity(patient));
        patientRepository.save(patientMapper.toEntity(patient2));

        Optional <PatientEntity> retrievedPatient = patientRepository.findById(patient.getNhi());
        assertThat(retrievedPatient.isPresent(), is(true));
        assertThat(retrievedPatient.get().getFirstName(), is("John"));

        // Update the patient's first name
        retrievedPatient.get().setFirstName("Jonathan");
        patientRepository.save(retrievedPatient.get());

        Optional <PatientEntity> updatedPatient = patientRepository.findById(patient.getNhi());
        assertThat(updatedPatient.isPresent(), is(true));
        assertThat(updatedPatient.get().getFirstName(), is("Jonathan"));
    }

    @Test
    void getPatientBookings() {
        patientRepository.save(patientMapper.toEntity(patient));
        patientRepository.save(patientMapper.toEntity(patient2));

        // Link the booking to the patient
        BookingEntity bookingEntity = bookingMapper.toEntity(booking1);
        bookingEntity.setPatient(patientMapper.toEntity(patient));
        bookingEntity.setServiceProvider(savedServiceProvider); // Link the managed entity
        bookingRepository.save(bookingEntity);

        List<BookingEntity> bookings = bookingRepository.findByPatientNhi(patient.getNhi());
        assertThat(bookings.size(), is(1));
        assertThat(bookings.get(0).getDestination(), is("Auckland"));
    }
}