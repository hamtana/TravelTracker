package com.CCDHB.api;

import com.CCDHB.NTA.entity.ServiceProviderEntity;
import com.CCDHB.NTA.mapper.ServiceProviderMapper;
import com.CCDHB.NTA.repository.ServiceProviderRepository;
import com.CCDHB.model.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*") // Allow requests from any origin
public class ServiceProvidersController implements ServiceProvidersApi {

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Autowired
    private ServiceProviderMapper serviceProviderMapper;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return ServiceProvidersApi.super.getRequest();
    }

    /**
     * POST /api/service-providers
     * Add a new service provider
     */
    @Override
    public ResponseEntity<Void> addServiceProvider(ServiceProvider serviceProvider) {
        ServiceProviderEntity entity = serviceProviderMapper.toEntity(serviceProvider);
        serviceProviderRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * GET /api/service-providers/{id}
     * Retrieve a single service provider by ID
     */
    @Override
    public ResponseEntity<ServiceProvider> getServiceProviderById(Integer id) {
        Optional<ServiceProviderEntity> serviceProviderEntity = serviceProviderRepository.findById(id);

        if (serviceProviderEntity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ServiceProvider dto = serviceProviderMapper.toDto(serviceProviderEntity.get());
        return ResponseEntity.ok(dto);
    }

    /**
     * GET /api/service-providers
     * Retrieve all service providers
     */
    @Override
    public ResponseEntity<List<ServiceProvider>> getServiceProviders() {
        List<ServiceProviderEntity> entities = serviceProviderRepository.findAll();

        if (entities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<ServiceProvider> serviceProviders = entities.stream()
                .map(serviceProviderMapper::toDto)
                .toList();

        return ResponseEntity.ok(serviceProviders);
    }

    /**
     * DELETE /api/service-providers/{id}
     */
    @Override
    public ResponseEntity<Void> deleteServiceProviderById(Integer id) {
        if (!serviceProviderRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        serviceProviderRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * PUT /api/service-providers/{id}
     */
    @Override
    public ResponseEntity<Void> updateServiceProviderById(Integer id, ServiceProvider serviceProvider) {
        if (!serviceProviderRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ServiceProviderEntity entity = serviceProviderMapper.toEntity(serviceProvider);
        entity.setId(id); // ensure we're updating the right one
        serviceProviderRepository.save(entity);

        return ResponseEntity.noContent().build();
    }
}
