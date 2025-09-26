package com.parkinglot.reservation.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.http.ResponseEntity;

import com.parkinglot.reservation.exception.InvalidRequestException;
import com.parkinglot.reservation.model.Floor;
import com.parkinglot.reservation.service.FloorService;

class FloorControllerTest {

    private FloorService floorService;
    private FloorController floorController;

    @BeforeEach
    void setUp() {
        floorService = mock(FloorService.class);
        floorController = new FloorController(floorService); // inject mock
    }

    @Test
    void testCreateFloorValid() {
        when(floorService.createFloor("Lobby")).thenReturn(new Floor("Lobby"));

        ResponseEntity<Floor> response = floorController.createFloor("Lobby");

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals("Lobby", response.getBody().getName());
    }

    @Test
    void testCreateFloorInvalid() {
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
            floorController.createFloor("");
        });

        assertEquals("Floor name cannot be empty", exception.getMessage());
    }
}
