package com.CCDHB.NTA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.CCDHB.NTA.entity.AccommodationEntity;

public interface AccommodationRepository extends JpaRepository<AccommodationEntity, String> {
}
