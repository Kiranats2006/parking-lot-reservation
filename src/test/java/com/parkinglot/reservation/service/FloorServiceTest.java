package com.parkinglot.reservation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.parkinglot.reservation.model.Floor;
import com.parkinglot.reservation.repository.FloorRepository;

class FloorServiceTest {

    private FloorRepository floorRepository;
    private FloorService floorService;

    @BeforeEach
    void setUp() {
        floorRepository = mock(FloorRepository.class);
        floorService = new FloorService(floorRepository); // inject mock
    }

    @Test
    void testCreateFloor() {
        Floor floor = new Floor("Basement");
        when(floorRepository.save(any(Floor.class))).thenReturn(floor);

        Floor result = floorService.createFloor("Basement");

        assertNotNull(result);
        assertEquals("Basement", result.getName());
        verify(floorRepository, times(1)).save(any(Floor.class));
    }
}
