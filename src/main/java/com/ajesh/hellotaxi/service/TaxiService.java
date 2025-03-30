package com.ajesh.hellotaxi.service;

import com.ajesh.hellotaxi.broker.BookingBroker;
import com.ajesh.hellotaxi.enums.BookingStatus;
import com.ajesh.hellotaxi.exception.TaxiAlreadyExistsException;
import com.ajesh.hellotaxi.exception.TaxiBrokerException;
import com.ajesh.hellotaxi.model.Booking;
import com.ajesh.hellotaxi.model.Taxi;
import com.ajesh.hellotaxi.repository.TaxiRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class responsible for managing taxi-related operations.
 * Handles taxi retrieval, registration, and status updates.
 */
@Service
public class TaxiService {

    private final TaxiRepository taxiRepository;

    private final BookingBroker bookingBroker;

    public TaxiService(TaxiRepository taxiRepository, BookingBroker bookingBroker) {
        this.taxiRepository = taxiRepository;
        this.bookingBroker = bookingBroker;
    }

    /**
     * Retrieves all taxis from the repository.
     *
     * @return a list of all taxis
     */
    public List<Taxi> getAllTaxis() {
        List<Taxi> taxisList = new ArrayList<>();
        taxiRepository.findAll().forEach(taxisList::add);
        return taxisList;
    }

    /**
     * Registers a new taxi and subscribes it to booking notifications via the broker.
     *
     * @param taxi the taxi to be registered
     * @return the registered taxi
     */
    @Transactional
    public Taxi addTaxi(Taxi taxi) {
        // Check for taxi duplication based license plate
        Optional<Taxi> existingTaxi = taxiRepository.findByLicensePlate(taxi.getLicensePlate());
        if (existingTaxi.isPresent()) {
            throw new TaxiAlreadyExistsException("Taxi with license plate " + taxi.getLicensePlate() + " already exists.");
        }

        Taxi updatedTaxi = taxiRepository.save(taxi);

        try {
            Long taxiId = updatedTaxi.getId();
            bookingBroker.registerTaxi(taxiId, bookingId -> {
                System.out.println("Taxi " + taxiId + " received booking notification: " + bookingId);
            });
        } catch (Exception e) {
            throw new TaxiBrokerException("Failed to register taxi with booking broker.");
        }

        return updatedTaxi;
    }

    /**
     * Updates the status of an existing taxi.
     *
     * @param taxi the taxi whose status needs to be updated
     * @return {@code true} if the update was successful, otherwise {@code false}
     */
    public boolean updateStatus(Taxi taxi) {
        int updatedRowsCount = taxiRepository.updateTaxiStatus(taxi.getId(), taxi.getStatus());
        return updatedRowsCount > 0;
    }
}
