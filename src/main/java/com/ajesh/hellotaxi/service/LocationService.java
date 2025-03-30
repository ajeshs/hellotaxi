package com.ajesh.hellotaxi.service;

import com.ajesh.hellotaxi.model.Location;
import com.ajesh.hellotaxi.model.Taxi;
import com.ajesh.hellotaxi.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        List<Location> locationList = new ArrayList<>();
        locationRepository.findAll().forEach(locationList::add);
        return locationList;
    }

    public Location addLocation(Location location) {
        return locationRepository.save(location);
    }
}
