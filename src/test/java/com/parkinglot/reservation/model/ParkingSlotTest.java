package com.parkinglot.reservation.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ParkingSlotTest {

    @Test
    public void testParkingSlotGettersAndSetters() {
        Floor floor = new Floor("Ground Floor");

        ParkingSlot slot = new ParkingSlot("A1", "4-wheeler", floor);

        assertEquals("A1", slot.getSlotNumber());
        assertEquals("4-wheeler", slot.getVehicleType());
        assertEquals(floor, slot.getFloor());

        // Test setters
        Floor newFloor = new Floor("First Floor");
        slot.setSlotNumber("B2");
        slot.setVehicleType("2-wheeler");
        slot.setFloor(newFloor);

        assertEquals("B2", slot.getSlotNumber());
        assertEquals("2-wheeler", slot.getVehicleType());
        assertEquals(newFloor, slot.getFloor());
    }
}
