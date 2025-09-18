package com.CCDHB.api;

import com.CCDHB.model.SupportPerson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "*") // Allow requests from any origin
public class SupportPersonsController implements SupportPersonsApi {

    private final HashMap<Integer, SupportPerson> supportPersons = new HashMap<>();

    @Override
    public ResponseEntity<SupportPerson> addSupportPerson(SupportPerson supportPerson) {
        if (supportPersons.containsKey(supportPerson.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Conflict
        }
        supportPersons.put(supportPerson.getId(), supportPerson);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // Created
    }

    @Override
    public ResponseEntity<Void> deleteSupporterById(Integer id) {
        if(supportPersons.containsKey(id)){
            supportPersons.remove(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // No Content
        }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Not Found
    }

    @Override
    public ResponseEntity<SupportPerson> getSupporterById(Integer id) {
        if(supportPersons.containsKey(id)){
            return ResponseEntity.ok(supportPersons.get(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Not Found
    }

    @Override
    public ResponseEntity<List<SupportPerson>> getSupporters() {
        if (supportPersons.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(List.copyOf(supportPersons.values()));
    }

    @Override
    public ResponseEntity<SupportPerson> updateSupporterById(Integer id, SupportPerson supportPerson) {
        if (!supportPersons.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Not Found
        }
        supportPerson.setId(id); // keep ID consistent
        supportPersons.put(id, supportPerson);
        return ResponseEntity.ok(supportPerson); // OK
    }
}
