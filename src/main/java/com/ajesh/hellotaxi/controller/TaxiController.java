package com.ajesh.hellotaxi.controller;

import com.ajesh.hellotaxi.model.Taxi;
import com.ajesh.hellotaxi.service.TaxiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaxiController {

    @Autowired
    TaxiService taxiService;

    @RequestMapping("/taxis")
    public List<Taxi> getAllTaxis() {
        return taxiService.getAllTaxis();
    }

    @RequestMapping(value = "/taxis/register", method = RequestMethod.POST)
    public ResponseEntity<Long> register(@RequestBody Taxi taxi) {
        Taxi registeredTaxi = taxiService.addTaxi(taxi);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredTaxi.getId());
    }

    @RequestMapping(value = "/taxis/status", method = RequestMethod.PATCH)
    public ResponseEntity<String> updateStatus(@RequestBody Taxi taxi) {
        boolean isUpdated = taxiService.updateStatus(taxi);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body("Taxi status updated successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Taxi not found!");
        }
    }

//    @RequestMapping(value = "/taxis/{taxiId}/location/{id}", method = RequestMethod.PATCH)
//    public String updateLocation(@RequestBody Taxi taxi) {
//        return "Success";
//    }
}
