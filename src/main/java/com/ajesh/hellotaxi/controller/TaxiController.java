package com.ajesh.hellotaxi.controller;

import com.ajesh.hellotaxi.model.Taxi;
import com.ajesh.hellotaxi.service.TaxiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing taxi-related operations.
 */
@RestController
public class TaxiController {

    @Autowired
    TaxiService taxiService;

    /**
     * Retrieves a list of all registered taxis.
     *
     * @return List of all taxis.
     */
    @GetMapping("/taxis")
    public List<Taxi> getAllTaxis() {
        return taxiService.getAllTaxis();
    }

    /**
     * Registers a new taxi in the system.
     *
     * @param taxi The taxi details to be registered.
     * @return The ID of the newly registered taxi.
     */
    @PostMapping("/taxis/register")
    public ResponseEntity<Long> register(@RequestBody Taxi taxi) {
        Taxi registeredTaxi = taxiService.addTaxi(taxi);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredTaxi.getId());
    }

    /**
     * Updates the status of a taxi (e.g., AVAILABLE, BOOKED).
     *
     * @param taxi The taxi object containing the new status.
     * @return Response indicating whether the update was successful or not.
     */
    @PatchMapping("/taxis/status")
    public ResponseEntity<String> updateStatus(@RequestBody Taxi taxi) {
        boolean isUpdated = taxiService.updateStatus(taxi);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body("Taxi status updated successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Taxi not found!");
        }
    }


//    @PatchMapping("/taxis/location")
//    public String updateLocation(@RequestBody Taxi taxi) {
//        return "Success";
//    }
}
