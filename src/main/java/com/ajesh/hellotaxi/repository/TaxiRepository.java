package com.ajesh.hellotaxi.repository;


import com.ajesh.hellotaxi.enums.TaxiStatus;
import com.ajesh.hellotaxi.model.Taxi;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TaxiRepository extends CrudRepository<Taxi, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Taxi t SET t.status = :status WHERE t.id = :id")
    int updateTaxiStatus(@Param("id") Long id, @Param("status") TaxiStatus status);
}
