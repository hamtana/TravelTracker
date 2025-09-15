package com.CCDHB.NTA.repository;

import com.CCDHB.NTA.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientEntity, String> {
}