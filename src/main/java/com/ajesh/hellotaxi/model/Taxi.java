package com.ajesh.hellotaxi.model;

import com.ajesh.hellotaxi.enums.TaxiStatus;
import jakarta.persistence.*;

@Entity
public class Taxi {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String licensePlate;

    @OneToOne
    private Driver driver;

    @ManyToOne
    private Location location;

    @Enumerated(EnumType.STRING)
    private TaxiStatus status;

    public TaxiStatus getStatus() {
        return status;
    }

    public void setStatus(TaxiStatus status) {
        this.status = status;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
