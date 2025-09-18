package com.CCDHB.NTA.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "Booking")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "patientNhi", nullable = false)
    private PatientEntity patient;

    @Column(name = "dateOfDeparture", nullable = false)
    private String dateOfDeparture;

    @Column(name = "dateOfReturn")
    private LocalDate dateOfReturn;

    @Column(name = "destination")
    private String destination;

    @Column(name = "bookingStatus")
    private String bookingStatus;

    @Column(name = "estimatedCost", nullable = false)
    private Double estimatedCost;

    @Column(name = "estimatedCostForPatient")
    private Double estimatedCostForPatient;

    @Column(name = "bookingCreatedAt", nullable = false)
    private OffsetDateTime bookingCreatedAt;

    @ManyToOne
    @JoinColumn(name = "serviceProviderId", nullable = false)
    private ServiceProviderEntity serviceProvider;

    @ManyToOne
    @JoinColumn(name = "accommodationAddress")
    private AccommodationEntity accommodation;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoteEntity> notes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }

    public String getDateOfDeparture() {
        return dateOfDeparture;
    }

    public void setDateOfDeparture(String dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public LocalDate getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(LocalDate dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public String getDestination() {
        return destination;
    }

    public List<NoteEntity> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteEntity> notes) {
        this.notes = notes;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(Double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public Double getEstimatedCostForPatient() {
        return estimatedCostForPatient;
    }

    public void setEstimatedCostForPatient(Double estimatedCostForPatient) {
        this.estimatedCostForPatient = estimatedCostForPatient;
    }

    public OffsetDateTime getBookingCreatedAt() {
        return bookingCreatedAt;
    }

    public void setBookingCreatedAt(OffsetDateTime bookingCreatedAt) {
        this.bookingCreatedAt = bookingCreatedAt;
    }

    public ServiceProviderEntity getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProviderEntity serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public AccommodationEntity getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(AccommodationEntity accommodation) {
        this.accommodation = accommodation;
    }
}
