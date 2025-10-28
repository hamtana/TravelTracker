package com.CCDHB.api;

import com.CCDHB.NTA.mapper.AccommodationMapper;
import com.CCDHB.NTA.repository.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import com.CCDHB.model.Accommodation;

import java.util.*;

@RestController
@CrossOrigin(origins = "*") // Allow requests from any origin
public class AccommodationController implements AccommodationsApi {


    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private AccommodationMapper accommodationMapper;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return AccommodationsApi.super.getRequest();
    }

    /**
     * Add a new accommodation.
     * @param accommodation  (required)
     * @return
     */
    @Override
    public ResponseEntity<Void> addAccommodation(Accommodation accommodation) {
        if(accommodationRepository.existsById(accommodation.getAddress())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            accommodationRepository.save(accommodationMapper.toEntity(accommodation));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteAccommodationByAddress(String address) {
        if(!accommodationRepository.existsById(address)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            accommodationRepository.deleteById(address);
            return ResponseEntity.ok().build();
        }
    }

    @Override
    public ResponseEntity<List<Accommodation>> getAccommodation() {
        List<Accommodation> accommodations = accommodationRepository.findAll().stream()
                .map(accommodationMapper::toDto)
                .toList();
        if(accommodations.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(accommodations);
        }
    }

    @Override
    public ResponseEntity<Accommodation> getAccommodationByAddress(String address) {
        Optional<Accommodation> accommodation = accommodationRepository.findById(address)
                .map(accommodationMapper::toDto);
        return accommodation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    public ResponseEntity<Void> updateAccommodationByAddress(String address, Accommodation accommodation) {
        if(!accommodationRepository.existsById(address)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            accommodation.setAddress(address); // Ensure the address remains unchanged
            accommodationRepository.save(accommodationMapper.toEntity(accommodation));
            return ResponseEntity.ok().build();
        }
    }
}
