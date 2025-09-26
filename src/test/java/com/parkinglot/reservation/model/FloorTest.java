package com.parkinglot.reservation.model;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class FloorTest {

    @Test
    void testFloorConstructorAndGetters() {
        Floor floor = new Floor("Ground Floor");

        assertEquals("Ground Floor", floor.getName());
        assertNotNull(floor.getSlots());
        assertTrue(floor.getSlots().isEmpty());
    }

    @Test
    void testSetters() {
        Floor floor = new Floor();
        floor.setName("First Floor");
        floor.setSlots(new ArrayList<>());

        assertEquals("First Floor", floor.getName());
        assertNotNull(floor.getSlots());
    }
}
