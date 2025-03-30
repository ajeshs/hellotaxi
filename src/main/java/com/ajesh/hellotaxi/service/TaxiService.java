package com.ajesh.hellotaxi.service;

import com.ajesh.hellotaxi.broker.BookingBroker;
import com.ajesh.hellotaxi.enums.BookingStatus;
import com.ajesh.hellotaxi.model.Booking;
import com.ajesh.hellotaxi.model.Taxi;
import com.ajesh.hellotaxi.repository.TaxiRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        Taxi updatedTaxi = taxiRepository.save(taxi);
        Long taxiId = updatedTaxi.getId();
        bookingBroker.registerTaxi(taxiId, bookingId -> {
            //check if taxi is available
            System.out.println("Taxi " + taxiId + " received booking notification: " + bookingId);
        });
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
