package com.ajesh.hellotaxi.controller;

import com.ajesh.hellotaxi.model.Location;
import com.ajesh.hellotaxi.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing location-related operations.
 */
@RestController
public class LocationController {

    @Autowired
    private LocationService locationService;

    /**
     * Retrieves a list of all registered locations.
     *
     * @return List of all locations.
     */
    @GetMapping("/locations")
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    /**
     * Registers a new location in the system.
     *
     * @param location The location details to be registered.
     * @return The ID of the newly registered location.
     */
    @PostMapping("/locations/register")
    public ResponseEntity<Long> register(@RequestBody Location location) {
        Location registeredLocation = locationService.addLocation(location);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredLocation.getId());
    }
}
