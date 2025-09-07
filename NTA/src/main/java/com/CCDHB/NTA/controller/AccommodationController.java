package com.CCDHB.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import com.CCDHB.model.Accommodation;

import java.util.*;

@RestController
public class AccommodationController implements AccommodationsApi {

    private final HashMap<String, Accommodation> accommodations = new HashMap<>();

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return AccommodationsApi.super.getRequest();
    }

    @Override
    public ResponseEntity<Void> addAccommodation(Accommodation accommodation) {
        if (accommodations.containsKey(accommodation.getAddress())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        accommodations.put(accommodation.getAddress(), accommodation);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> deleteAccommodationByAddress(String address) {
       if(accommodations.containsKey(address)){
           accommodations.remove(address);
           return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
       }
       return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<List<Accommodation>> getAccommodation() {
       if(accommodations.isEmpty()){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }
       return ResponseEntity.ok(new ArrayList<>(accommodations.values()));
    }

    @Override
    public ResponseEntity<Accommodation> getAccommodationByAddress(String address) {
        if(accommodations.containsKey(address)){
            return ResponseEntity.ok(accommodations.get(address));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Void> updateAccommodationByAddress(String address, Accommodation accommodation) {
        if(!accommodations.containsKey(address)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        accommodation.setAddress(address); // keep ID consistent
        accommodations.put(address, accommodation);
        return ResponseEntity.ok().build();
    }
}
