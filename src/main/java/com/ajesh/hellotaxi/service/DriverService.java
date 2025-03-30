package com.ajesh.hellotaxi.service;

import com.ajesh.hellotaxi.model.Driver;
import com.ajesh.hellotaxi.model.Location;
import com.ajesh.hellotaxi.repository.DriverRepository;
import com.ajesh.hellotaxi.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public List<Driver> getAllDrivers() {
        List<Driver> driverList = new ArrayList<>();
        driverRepository.findAll().forEach(driverList::add);
        return driverList;
    }

    public Driver addDriver(Driver driver) {
        return driverRepository.save(driver);
    }
}
