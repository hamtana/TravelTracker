package com.CCDHB.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.CCDHB.model.ServiceProvider;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@RestController
public class ServiceProvidersController implements ServiceProvidersApi {

    // Implement the methods defined in ServiceProvidersApi here
    private final HashMap<Integer, ServiceProvider> serviceProviders = new HashMap<>();

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return ServiceProvidersApi.super.getRequest();
    }

    @Override
    public ResponseEntity<Void> addServiceProvider(ServiceProvider serviceProvider) {
        if (serviceProviders.containsKey(serviceProvider.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Conflict
        }
        serviceProviders.put(serviceProvider.getId(), serviceProvider);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // Created
    }

    @Override
    public ResponseEntity<Void> deleteServiceProviderById(Integer id) {
        if(serviceProviders.containsKey(id)){
            serviceProviders.remove(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // No Content
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Not Found
        }
    }

    @Override
    public ResponseEntity<ServiceProvider> getServiceProviderById(Integer id) {
        return ServiceProvidersApi.super.getServiceProviderById(id);
    }

    @Override
    public ResponseEntity<List<ServiceProvider>> getServiceProviders() {
        return ServiceProvidersApi.super.getServiceProviders();
    }

    @Override
    public ResponseEntity<Void> updateServiceProviderById(Integer id, ServiceProvider serviceProvider) {
        return ServiceProvidersApi.super.updateServiceProviderById(id, serviceProvider);
    }
}
