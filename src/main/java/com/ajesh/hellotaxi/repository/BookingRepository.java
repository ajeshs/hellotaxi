package com.ajesh.hellotaxi.repository;

import com.ajesh.hellotaxi.enums.BookingStatus;
import com.ajesh.hellotaxi.model.Booking;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Booking> findById(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Booking b SET b.status = :status WHERE b.id = :id AND b.status != :status")
    int completeBooking(@Param("id") Long id, @Param("status") BookingStatus status);

    @Query("SELECT COUNT(b) FROM Booking b")
    long getTotalBookings();

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = :status")
    long getBookingsByStatus(@Param("status") BookingStatus status);

    @Query("SELECT b.taxi.id, COUNT(b) FROM Booking b GROUP BY b.taxi.id ORDER BY COUNT(b) DESC")
    List<Object[]> getMostActiveTaxis();

    @Query("SELECT CAST(b.bookingTime AS date), COUNT(b) FROM Booking b GROUP BY CAST(b.bookingTime AS date)")
    List<Object[]> getBookingsPerDay();

    List<Booking> findByStatus(BookingStatus status);
}
