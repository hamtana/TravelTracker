package com.CCDHB.NTA.repository;

import com.CCDHB.NTA.entity.BookingEntity;
import com.CCDHB.NTA.entity.SupportPersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportPersonRepository extends JpaRepository<SupportPersonEntity, Integer> {
    List<SupportPersonEntity> findByPatientNhi(String nhi);
}
