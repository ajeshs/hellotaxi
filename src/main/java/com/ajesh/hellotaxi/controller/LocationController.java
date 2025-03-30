package com.ajesh.hellotaxi.controller;

import com.ajesh.hellotaxi.model.Location;
import com.ajesh.hellotaxi.model.Taxi;
import com.ajesh.hellotaxi.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/locations")
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    @PostMapping("/locations/register")
    public ResponseEntity<Long> register(@RequestBody Location location) {
        Location registeredLocation = locationService.addLocation(location);
//        return ResponseEntity.ok("Location " + location.getName() + " registered successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredLocation.getId());
    }
}
