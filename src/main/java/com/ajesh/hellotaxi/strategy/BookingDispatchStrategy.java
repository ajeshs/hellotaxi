package com.ajesh.hellotaxi.strategy;

import java.util.Map;
import java.util.function.Consumer;

public interface BookingDispatchStrategy {
    void dispatch(Long bookingId, Map<Long, Consumer<Long>> taxiSubscribers);
}
