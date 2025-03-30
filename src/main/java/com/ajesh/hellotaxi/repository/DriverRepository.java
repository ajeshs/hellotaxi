package com.ajesh.hellotaxi.repository;

import com.ajesh.hellotaxi.model.Driver;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends CrudRepository<Driver, Long> {

}
