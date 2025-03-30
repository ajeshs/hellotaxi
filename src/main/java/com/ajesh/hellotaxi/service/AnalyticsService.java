package com.ajesh.hellotaxi.service;

import com.ajesh.hellotaxi.enums.BookingStatus;
import com.ajesh.hellotaxi.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {

    @Autowired
    private BookingRepository bookingRepository;

    public Map<String, Object> getBookingAnalytics() {
        Map<String, Object> analytics = new HashMap<>();

        analytics.put("totalBookings", bookingRepository.getTotalBookings());
        analytics.put("completedBookings", bookingRepository.getBookingsByStatus(BookingStatus.COMPLETED));
        analytics.put("canceledBookings", bookingRepository.getBookingsByStatus(BookingStatus.CANCELLED));

        List<Object[]> bookingsPerDay = bookingRepository.getBookingsPerDay();
        analytics.put("bookingsPerDay", bookingsPerDay);

        List<Object[]> activeTaxis = bookingRepository.getMostActiveTaxis();
        analytics.put("mostActiveTaxis", activeTaxis);

        return analytics;
    }
}
