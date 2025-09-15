package com.CCDHB.NTA.controller;

import com.CCDHB.NTA.repository.PatientRepository;
import com.CCDHB.model.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class PatientControllerTest {

    // Using HashMap Local testing
   //  HashMap<String, Patient> patients = new HashMap<>();

    @Autowired
    private PatientRepository patients;

    Patient patient = new Patient();
    Patient patient2 = new Patient();
    Patient patient3 = new Patient();

    Booking booking1 = new Booking();
    ServiceProvider serviceProvider1 = new ServiceProvider();
    Accommodation accommodation1 = new Accommodation();
    SupportPerson supportPerson1 = new SupportPerson();
    BookedSupportPerson bsp = new BookedSupportPerson();

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

        serviceProvider1.setId(1);
        serviceProvider1.setName("Health Services Ltd");
        serviceProvider1.setAddress("123 Health St, Wellness City");

        accommodation1.setAddress("456 Stay Ave, Comfort Town");
        accommodation1.setName("Comfort Inn");

        // Creating Support person
        supportPerson1.setId(1);
        supportPerson1.setFirstName("Emily");
        supportPerson1.setSurname("Brown");
        supportPerson1.setCoveredByNta(true);
        supportPerson1.setPatient(patient);

        // assigning support person as Booked Support Person
        bsp.setSupportPerson(supportPerson1);

        booking1.setId(1);
        booking1.setDateOfDeparture(LocalDate.of(2025, 9, 15));
        booking1.setDateOfReturn(LocalDate.of(2025, 9, 20));
        booking1.setBookingStatus("Completed");
        booking1.setEstimatedCost(1500.0f);
        booking1.setEstimatedCostForPatient(0.0f);
        booking1.setBookingCreatedAt(OffsetDateTime.now());
        booking1.setServiceProvider(serviceProvider1);
        booking1.setAccommodation(accommodation1);
        booking1.setDestination("Auckland");
        booking1.setSupportPersons(List.of(bsp));

        patient.setBookings(List.of(booking1));


    }

    @AfterEach
    void tearDown() {
        patients.clear(); //remove the contents of the map after each test.
    }

    @Test
    void addPatient() {
        patients.put(patient.getNhi(), patient);
        patients.put(patient2.getNhi(), patient2);

        assertThat(patients, hasKey("ABC1234"));
        assertThat(patients, hasKey("XYZ5678"));
        assertThat(patients, not(hasKey("LMN8901")));

    }

    @Test
    void deletePatientById() {

        patients.put(patient.getNhi(), patient);
        patients.put(patient2.getNhi(), patient2);

        assertThat(patients, hasKey("ABC1234"));
        assertThat(patients, hasKey("XYZ5678"));

        patients.remove("ABC1234");

        assertThat(patients, not(hasKey("ABC1234")));
        assertThat(patients, hasKey("XYZ5678"));
    }

    @Test
    void getPatientById() {
        patients.put(patient.getNhi(), patient);
        patients.put(patient2.getNhi(), patient2);

        assertThat(patients, hasKey("ABC1234"));
        assertThat(patients, hasKey("XYZ5678"));

        Patient retrievedPatient = patients.get("ABC1234");
        assertNotNull(retrievedPatient);
        assertEquals("John", retrievedPatient.getFirstName());
        assertEquals("Doe", retrievedPatient.getSurname());

        Patient nonExistentPatient = patients.get("LMN8901");
        assertNull(nonExistentPatient);
    }

    @Test
    void getPatients() {
        patients.put(patient.getNhi(), patient);
        patients.put(patient2.getNhi(), patient2);

        assertThat(patients.size(), is(2));
        assertThat(patients.values(), containsInAnyOrder(patient, patient2));
    }

    @Test
    void updatePatientById() {
        patients.put(patient.getNhi(), patient);
        patients.put(patient2.getNhi(), patient2);

        assertThat(patients, hasKey("ABC1234"));
        assertThat(patients, hasKey("XYZ5678"));

        // Update patient2's first name
        Patient updatedPatient = new Patient();
        updatedPatient.setNhi("XYZ5678");
        updatedPatient.setFirstName("Janet");
        updatedPatient.setSurname("Smith");
        updatedPatient.setNtaNumber("8765432");

        patients.put(updatedPatient.getNhi(), updatedPatient);

        Patient retrievedPatient = patients.get("XYZ5678");
        assertNotNull(retrievedPatient);
        assertEquals("Janet", retrievedPatient.getFirstName());
        assertEquals("Smith", retrievedPatient.getSurname());
    }

    @Test
    void getPatientBookings() {

        patients.put(patient.getNhi(), patient);
        patients.put(patient2.getNhi(), patient2);

        Patient retrievedPatient = patients.get("ABC1234");
        assertNotNull(retrievedPatient);

        List<Booking> bookings = retrievedPatient.getBookings();
        assertNotNull(bookings);
        assertThat(bookings.size(), is(1));
        assertThat(bookings, contains(booking1));



    }
}