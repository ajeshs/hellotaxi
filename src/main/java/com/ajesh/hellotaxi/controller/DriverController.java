package com.ajesh.hellotaxi.controller;

import com.ajesh.hellotaxi.model.Driver;
import com.ajesh.hellotaxi.model.Location;
import com.ajesh.hellotaxi.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing driver-related operations.
 */
@RestController
public class DriverController {

    @Autowired
    private DriverService driverService;

    /**
     * Retrieves a list of all registered drivers.
     *
     * @return List of all drivers.
     */
    @GetMapping("/drivers")
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    /**
     * Registers a new driver in the system.
     *
     * @param driver The driver details to be registered.
     * @return The ID of the newly registered driver.
     */
    @PostMapping("/drivers/register")
    public ResponseEntity<Long> register(@RequestBody Driver driver) {
        Driver registeredDriver = driverService.addDriver(driver);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredDriver.getId());
    }
}
