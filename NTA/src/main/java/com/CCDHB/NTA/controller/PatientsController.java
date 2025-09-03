package com.CCDHB.NTA.controller;

import com.CCDHB.api.PatientsApi;
import com.CCDHB.model.Patient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PatientsController implements PatientsApi {

    private final List<Patient> patients = new ArrayList<>(); //uses collections

    @Override
    public ResponseEntity<List<Patient>> patientsGet() {
        return ResponseEntity.status(201).body(patients);
    }

    @Override
    public ResponseEntity<Patient> patientsPost(Patient patient) {
        patients.add(patient);
        return ResponseEntity.status(201).body(patient);
    }

    @Override
    public ResponseEntity<Patient> patientsNhiGet(String nhi) {
        return patients.stream()
                .filter(p -> p.getNhi().equals(nhi))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
