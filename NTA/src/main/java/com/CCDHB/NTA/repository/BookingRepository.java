package com.CCDHB.NTA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.CCDHB.NTA.entity.BookingEntity;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {
    List<BookingEntity> findByPatientNhi(String nhi);
}
