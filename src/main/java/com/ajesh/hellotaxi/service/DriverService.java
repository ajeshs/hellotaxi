package com.ajesh.hellotaxi.service;

import com.ajesh.hellotaxi.model.Driver;
import com.ajesh.hellotaxi.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for managing driver-related operations.
 * Provides functionality to retrieve and register drivers.
 */
@Service
public class DriverService {

    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    /**
     * Retrieves all registered drivers from the repository.
     *
     * @return a list of all drivers
     */
    public List<Driver> getAllDrivers() {
        List<Driver> driverList = new ArrayList<>();
        driverRepository.findAll().forEach(driverList::add);
        return driverList;
    }

    /**
     * Registers a new driver in the system.
     *
     * @param driver the driver to be registered
     * @return the registered driver
     */
    public Driver addDriver(Driver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver details cannot be null.");
        }
        return driverRepository.save(driver);
    }
}
