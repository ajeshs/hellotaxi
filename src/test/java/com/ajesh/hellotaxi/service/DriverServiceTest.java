package com.ajesh.hellotaxi.service;

import com.ajesh.hellotaxi.model.Driver;
import com.ajesh.hellotaxi.repository.DriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DriverServiceTest {

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverService driverService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDrivers_ShouldReturnListOfDrivers() {
        Driver driver1 = new Driver();
        driver1.setId(1L);
        Driver driver2 = new Driver();
        driver2.setId(2L);

        when(driverRepository.findAll()).thenReturn(Arrays.asList(driver1, driver2));

        List<Driver> result = driverService.getAllDrivers();

        assertEquals(2, result.size());
        verify(driverRepository, times(1)).findAll();
    }

    @Test
    void addDriver_ShouldSaveAndReturnDriver() {
        Driver driver = new Driver();
        driver.setId(1L);

        when(driverRepository.save(any(Driver.class))).thenReturn(driver);

        Driver result = driverService.addDriver(driver);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(driverRepository, times(1)).save(driver);
    }
}
