package com.CCDHB.NTA.repository;

import com.CCDHB.NTA.entity.ServiceProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceProviderRepository extends JpaRepository<ServiceProviderEntity, Integer> {
}
