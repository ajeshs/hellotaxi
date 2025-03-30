package com.ajesh.hellotaxi.strategy;

import com.ajesh.hellotaxi.broker.BookingBroker;
import com.ajesh.hellotaxi.enums.TaxiStatus;
import com.ajesh.hellotaxi.repository.TaxiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Consumer;

public class AvailableStatusStrategy implements BookingDispatchStrategy {

    private final TaxiRepository taxiRepository;

    public AvailableStatusStrategy(TaxiRepository taxiRepository) {
        this.taxiRepository = taxiRepository;
    }

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
