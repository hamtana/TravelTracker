package com.CCDHB.api;

import com.CCDHB.NTA.entity.PatientEntity;
import com.CCDHB.NTA.entity.SupportPersonEntity;
import com.CCDHB.NTA.mapper.SupportPersonMapper;
import com.CCDHB.NTA.mapper.SupportPersonMapperImpl;
import com.CCDHB.NTA.repository.SupportPersonRepository;
import com.CCDHB.model.Patient;
import com.CCDHB.model.SupportPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*") // Allow requests from any origin
public class SupportPersonsController implements SupportPersonsApi {

    @Autowired
    SupportPersonRepository supportPersonRepository;

    @Autowired
    SupportPersonMapper supportPersonMapper;

    /**
     * Add a new support person.
     * @param supportPerson  (required)
     * @return
     */
    @Override
    public ResponseEntity<SupportPerson> addSupportPerson(SupportPerson supportPerson) {
        if (supportPersonRepository.findById(supportPerson.getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Conflict
        }
        supportPersonRepository.save(supportPersonMapper.toEntity(supportPerson));
        return ResponseEntity.status(HttpStatus.CREATED).build(); // Created
    }


    /**
     * Get all Support Persons.
     * @return List of Support Persons
     */
    @Override
    public ResponseEntity<List<SupportPerson>> getSupporters() {
        List<SupportPersonEntity> supportPersonEntities = supportPersonRepository.findAll();
        if (supportPersonEntities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<SupportPerson> supportPersonList = supportPersonEntities.stream()
                .map(supportPersonMapper::toDto)
                .toList();
        return ResponseEntity.ok(supportPersonList);
    }

    @Override
    public ResponseEntity<Void> deleteSupporterById(Integer id, String nhi) {
        SupportPersonEntity supportPersonEntity = supportPersonRepository.findById(id).orElse(null);
        if (supportPersonEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // Delete the Support Person
        supportPersonRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // No Content
    }

    @Override
    public ResponseEntity<SupportPerson> getSupporterById(Integer id, String nhi) {
        Optional<SupportPersonEntity> supportPersonEntityOpt = supportPersonRepository.findById(id);
        if (supportPersonEntityOpt.isPresent() && supportPersonEntityOpt.get().getPatient().getNhi().equals(nhi)) {
            SupportPerson supportPerson = supportPersonMapper.toDto(supportPersonEntityOpt.get());
            return new ResponseEntity<>(supportPerson, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<SupportPerson>> getSupportersForPatient(String nhi) {
        List<SupportPersonEntity> supportPersonEntities = supportPersonRepository.findByPatientNhi(nhi);
        if (supportPersonEntities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<SupportPerson> supportPersonList = supportPersonEntities.stream()
                .map(supportPersonMapper::toDto)
                .toList();
        return ResponseEntity.ok(supportPersonList);
    }

    @Override
    public ResponseEntity<SupportPerson> updateSupporterById(Integer id, String nhi, SupportPerson supportPerson) {
        Optional<SupportPersonEntity> existingOpt = supportPersonRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        SupportPersonEntity existingEntity = existingOpt.get();

        // Use MapStruct update method or manual copying
        supportPersonMapper.updateEntityFromDto(supportPerson, existingEntity);

        supportPersonRepository.save(existingEntity);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
