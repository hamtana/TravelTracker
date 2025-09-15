package com.CCDHB.NTA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.CCDHB.NTA.entity.BookingEntity;

public interface BookingRepository extends JpaRepository<BookingEntity, String> {
}
