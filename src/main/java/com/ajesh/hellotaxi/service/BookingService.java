package com.ajesh.hellotaxi.service;

import com.ajesh.hellotaxi.broker.BookingBroker;
import com.ajesh.hellotaxi.enums.BookingStatus;
import com.ajesh.hellotaxi.enums.TaxiStatus;
import com.ajesh.hellotaxi.model.Booking;
import com.ajesh.hellotaxi.model.Taxi;
import com.ajesh.hellotaxi.repository.BookingRepository;
import com.ajesh.hellotaxi.repository.TaxiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    private final BookingBroker bookingBroker;

    private final TaxiRepository taxiRepository;

    public BookingService(BookingRepository bookingRepository, BookingBroker bookingBroker, TaxiRepository taxiRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingBroker = bookingBroker;
        this.taxiRepository = taxiRepository;
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookingList = new ArrayList<>();
        bookingRepository.findAll().forEach(bookingList::add);
        return bookingList;
    }

    public Booking createBooking(Booking booking) {
        booking.setStatus(BookingStatus.INITIATED);
        Booking savedBooking = bookingRepository.save(booking);
        bookingBroker.publishBooking(savedBooking.getId(), savedBooking.getCategory());
        return savedBooking;
    }

    // Accept a booking (only one taxi can succeed)
    @Transactional
    public boolean acceptBooking(Booking booking) {
        Optional<Booking> optionalBooking = bookingRepository.findById(booking.getId());

        if (optionalBooking.isPresent()) {
            Booking existingBooking = optionalBooking.get();

            // Check if the booking is still available
            if (existingBooking.getStatus() == BookingStatus.INITIATED) {
                Taxi taxi = taxiRepository.findById(booking.getTaxi().getId()).orElse(null);
                if (taxi == null || taxi.getStatus() != TaxiStatus.AVAILABLE) {
                    return false;
                }
                existingBooking.setStatus(BookingStatus.ACCEPTED);
                taxi.setStatus(TaxiStatus.BOOKED);
                existingBooking.setTaxi(taxi);
                bookingRepository.save(existingBooking);
                return true;
            }
        }
        return false;
    }

    public boolean pickupBooking(Booking booking) {
        Booking existingBooking = bookingRepository.findById(booking.getId()).orElse(null);
        if (existingBooking != null && existingBooking.getStatus() == BookingStatus.ACCEPTED) {
            existingBooking.setStatus(BookingStatus.PICKEDUP);
            bookingRepository.save(existingBooking);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean completeBooking(Booking booking) {
        Booking existingBooking = bookingRepository.findById(booking.getId()).orElse(null);
        if (existingBooking != null && existingBooking.getStatus() == BookingStatus.PICKEDUP) {
            existingBooking.setStatus(BookingStatus.COMPLETED);
            Taxi taxi = existingBooking.getTaxi();
            if (taxi != null) {
                taxi.setStatus(TaxiStatus.AVAILABLE);
                taxiRepository.save(taxi);
            }
            bookingRepository.save(existingBooking);
            return true;
        }
        return false;
    }


}
