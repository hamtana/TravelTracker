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
     * Delete a Support Person by Id.
     * @param id  (required)
     * @return
     */
    @Override
    public ResponseEntity<Void> deleteSupporterById(Integer id) {
        if(supportPersonRepository.findById(id).isPresent()){
            supportPersonRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // No Content
        }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Not Found
    }

    /**
     * Get a Support Person by Id.
     * @param id  (required)
     * @return
     */
    @Override
    public ResponseEntity<SupportPerson> getSupporterById(Integer id) {
        if(supportPersonRepository.findById(id).isPresent()){
            return ResponseEntity.ok(supportPersonMapper.toDto(supportPersonRepository.findById(id).get())); // OK
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Not Found
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
    public ResponseEntity<SupportPerson> updateSupporterById(Integer id, SupportPerson supportPerson) {
        if (!supportPersonRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Not Found
        }
        supportPerson.setId(id); // keep ID consistent
        SupportPersonEntity supportPersonEntity = supportPersonMapper.toEntity(supportPerson);
        supportPersonRepository.save(supportPersonEntity);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
