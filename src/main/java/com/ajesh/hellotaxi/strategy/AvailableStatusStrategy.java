package com.ajesh.hellotaxi.strategy;

import com.ajesh.hellotaxi.enums.TaxiStatus;
import com.ajesh.hellotaxi.repository.TaxiRepository;

import java.util.Map;
import java.util.function.Consumer;

/**
 * A booking dispatch strategy that sends booking notifications only to available taxis.
 * This ensures that only taxis with an AVAILABLE status receive booking requests.
 */
public class AvailableStatusStrategy implements BookingDispatchStrategy {

    private final TaxiRepository taxiRepository;

    public AvailableStatusStrategy(TaxiRepository taxiRepository) {
        this.taxiRepository = taxiRepository;
    }

    /**
     * Dispatches a booking request to all registered taxis that are currently available.
     *
     * @param bookingId       the ID of the booking to be dispatched
     * @param taxiSubscribers a map of registered taxis and their respective booking handlers
     */
    @Override
    public void dispatch(Long bookingId, Map<Long, Consumer<Long>> taxiSubscribers) {
        System.out.println("Available Strategy: Sending booking " + bookingId + " only to available taxis...");
        for (Map.Entry<Long, Consumer<Long>> entry : taxiSubscribers.entrySet()) {
            Long taxiId = entry.getKey();
            taxiRepository.findById(taxiId)
                    .filter(taxi -> TaxiStatus.AVAILABLE.equals(taxi.getStatus()))
                    .ifPresent(taxi -> entry.getValue().accept(bookingId));
        }
    }
}
