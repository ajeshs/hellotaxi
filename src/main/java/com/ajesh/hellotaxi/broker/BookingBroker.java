package com.ajesh.hellotaxi.broker;

import com.ajesh.hellotaxi.enums.BookingCategory;
import com.ajesh.hellotaxi.strategy.BookingDispatchStrategy;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * Manages the dispatching of bookings to registered taxis based on different dispatch strategies.
 * Taxis register themselves to receive booking notifications, and bookings are published
 * using the appropriate strategy based on their category.
 */
@Component
public class BookingBroker {

    private final Map<Long, Consumer<Long>> taxiSubscribers = new ConcurrentHashMap<>();

    private final Map<BookingCategory, BookingDispatchStrategy> dispatchStrategies;

    public BookingBroker(Map<BookingCategory, BookingDispatchStrategy> dispatchStrategies) {
        this.dispatchStrategies = dispatchStrategies;
    }

    /**
     * Registers a taxi to receive booking notifications.
     *
     * @param taxiId         the ID of the taxi
     * @param bookingHandler a consumer function that handles booking notifications
     */
    public void registerTaxi(Long taxiId, Consumer<Long> bookingHandler) {
        taxiSubscribers.put(taxiId, bookingHandler);
    }

    /**
     * Publishes a booking to the registered taxis using the appropriate dispatch strategy.
     * If no specific strategy is found for the booking category, it defaults to the strategy for PREMIUM bookings.
     *
     * @param bookingId the ID of the booking
     * @param category  the category of the booking
     */
    public void publishBooking(Long bookingId, BookingCategory category) {
        BookingDispatchStrategy strategy = dispatchStrategies.get(category);

        if (strategy == null) {
            strategy = dispatchStrategies.get(BookingCategory.PREMIUM);
        }

        strategy.dispatch(bookingId, taxiSubscribers);
    }
}
