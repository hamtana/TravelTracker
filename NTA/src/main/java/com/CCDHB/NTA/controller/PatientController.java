package com.CCDHB.api;

import com.CCDHB.model.Patient;
import com.CCDHB.model.Booking;
import com.CCDHB.model.SupportPerson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class PatientController implements PatientsApi {

    // Follows the API Specification PatientApi interface
    // In-memory store of patients by NHI
    private final HashMap<String, Patient> patients = new HashMap<>();

    @Override
    public ResponseEntity<Patient> addPatient(Patient patient) {
        if (patients.containsKey(patient.getNhi())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        patients.put(patient.getNhi(), patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }

    @Override
    public ResponseEntity<Void> deletePatientById(String nhi) {
        if (patients.remove(nhi) != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Patient> getPatientById(String nhi) {
        Patient patient = patients.get(nhi);
        if (patient != null) {
            return ResponseEntity.ok(patient);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<Patient>> getPatients() {
        if (patients.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(new ArrayList<>(patients.values()));
    }

    @Override
    public ResponseEntity<Patient> updatePatientById(String nhi, Patient patient) {
        if (!patients.containsKey(nhi)) {
            return ResponseEntity.notFound().build();
        }
        patient.setNhi(nhi); // keep ID consistent
        patients.put(nhi, patient);
        return ResponseEntity.ok(patient);
    }

    @Override
    public ResponseEntity<List<Booking>> getPatientBookings(String nhi) {
        Patient patient = patients.get(nhi);
        if (patient != null) {
            return ResponseEntity.ok(patient.getBookings());
        }
        return ResponseEntity.notFound().build();
    }

}
