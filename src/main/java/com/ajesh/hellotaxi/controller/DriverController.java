package com.ajesh.hellotaxi.controller;

import com.ajesh.hellotaxi.model.Driver;
import com.ajesh.hellotaxi.model.Location;
import com.ajesh.hellotaxi.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DriverController {

    @Autowired
    private DriverService driverService;

    @GetMapping("/drivers")
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @PostMapping("/drivers/register")
    public ResponseEntity<Long> register(@RequestBody Driver driver) {
        Driver registeredDriver = driverService.addDriver(driver);
//        return ResponseEntity.ok("Driver " + driver.getName() + " registered successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredDriver.getId());
    }
}
