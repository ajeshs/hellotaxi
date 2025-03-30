package com.ajesh.hellotaxi.service;

import com.ajesh.hellotaxi.model.Location;
import com.ajesh.hellotaxi.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllLocations_ShouldReturnListOfLocations() {
        Location location1 = new Location();
        location1.setId(1L);
        Location location2 = new Location();
        location2.setId(2L);

        when(locationRepository.findAll()).thenReturn(Arrays.asList(location1, location2));

        List<Location> result = locationService.getAllLocations();

        assertEquals(2, result.size());
        verify(locationRepository, times(1)).findAll();
    }

    @Test
    void addLocation_ShouldSaveAndReturnLocation() {
        Location location = new Location();
        location.setId(1L);

        when(locationRepository.save(any(Location.class))).thenReturn(location);

        Location result = locationService.addLocation(location);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(locationRepository, times(1)).save(location);
    }
}
