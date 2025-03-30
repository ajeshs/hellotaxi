package com.ajesh.hellotaxi.strategy;

import java.util.Map;
import java.util.function.Consumer;

public class DefaultStrategy implements BookingDispatchStrategy {
    @Override
    public void dispatch(Long bookingId, Map<Long, Consumer<Long>> taxiSubscribers) {
        System.out.println("Default Strategy: Sending booking " + bookingId + " to all taxis...");
        for (Consumer<Long> handler : taxiSubscribers.values()) {
            handler.accept(bookingId);
        }
    }
}
