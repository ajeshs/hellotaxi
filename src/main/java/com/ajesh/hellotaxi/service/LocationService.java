package com.ajesh.hellotaxi.service;

import com.ajesh.hellotaxi.model.Location;
import com.ajesh.hellotaxi.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for managing location-related operations.
 * Provides functionality to retrieve and register locations.
 */
@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    /**
     * Retrieves all registered locations from the repository.
     *
     * @return a list of all locations
     */
    public List<Location> getAllLocations() {
        List<Location> locationList = new ArrayList<>();
        locationRepository.findAll().forEach(locationList::add);
        return locationList;
    }

    /**
     * Registers a new location in the system.
     *
     * @param location the location to be registered
     * @return the registered location
     */
    public Location addLocation(Location location) {
        return locationRepository.save(location);
    }
}
