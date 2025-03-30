package com.ajesh.hellotaxi.broker;

import com.ajesh.hellotaxi.enums.BookingCategory;
import com.ajesh.hellotaxi.strategy.BookingDispatchStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;

class BookingBrokerTest {

    private BookingBroker bookingBroker;

    @Mock
    private BookingDispatchStrategy normalStrategy;

    @Mock
    private BookingDispatchStrategy premiumStrategy;

    @Mock
    private Consumer<Long> taxiConsumer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Map<BookingCategory, BookingDispatchStrategy> strategyMap = Map.of(
                BookingCategory.NORMAL, normalStrategy,
                BookingCategory.PREMIUM, premiumStrategy
        );
        bookingBroker = new BookingBroker(strategyMap);
    }

    @Test
    void testRegisterTaxi() {
        Long taxiId = 1L;
        bookingBroker.registerTaxi(taxiId, taxiConsumer);

        bookingBroker.publishBooking(100L, BookingCategory.NORMAL);
        verify(normalStrategy).dispatch(eq(100L), any(ConcurrentHashMap.class));
    }

    @Test
    void testPublishBookingWithNormalCategory() {
        Long bookingId = 200L;
        bookingBroker.publishBooking(bookingId, BookingCategory.NORMAL);
        verify(normalStrategy).dispatch(eq(bookingId), any(ConcurrentHashMap.class));
    }

    @Test
    void testPublishBookingWithPremiumCategory() {
        Long bookingId = 300L;
        bookingBroker.publishBooking(bookingId, BookingCategory.PREMIUM);
        verify(premiumStrategy).dispatch(eq(bookingId), any(ConcurrentHashMap.class));
    }
}
