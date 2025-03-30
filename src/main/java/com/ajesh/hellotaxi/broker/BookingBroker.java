package com.ajesh.hellotaxi.broker;

import com.ajesh.hellotaxi.enums.BookingCategory;
import com.ajesh.hellotaxi.strategy.AvailableStatusStrategy;
import com.ajesh.hellotaxi.strategy.BookingDispatchStrategy;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Component
public class BookingBroker {

    private final Map<Long, Consumer<Long>> taxiSubscribers = new ConcurrentHashMap<>();

    private final Map<BookingCategory, BookingDispatchStrategy> dispatchStrategies;

    public BookingBroker(Map<BookingCategory, BookingDispatchStrategy> dispatchStrategies) {
        this.dispatchStrategies = dispatchStrategies;
    }

    public void registerTaxi(Long taxiId, Consumer<Long> bookingHandler) {
        taxiSubscribers.put(taxiId, bookingHandler);
    }

    public void publishBooking(Long bookingId, BookingCategory category) {
        BookingDispatchStrategy strategy = dispatchStrategies.getOrDefault(category, dispatchStrategies.get(BookingCategory.PREMIUM));
        strategy.dispatch(bookingId, taxiSubscribers);
    }
}
