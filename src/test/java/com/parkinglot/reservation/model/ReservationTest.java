package com.parkinglot.reservation.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class ReservationTest {

    @Test
    void testReservationConstructorAndGetters() {
        ParkingSlot slot = new ParkingSlot("A1", "4-wheeler", null);

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(2);

        Reservation reservation = new Reservation(
                "KA01AB1234",
                "4-wheeler",
                start,
                end,
                60,
                slot
        );

        assertEquals("KA01AB1234", reservation.getVehicleNumber());
        assertEquals("4-wheeler", reservation.getVehicleType());
        assertEquals(start, reservation.getStartTime());
        assertEquals(end, reservation.getEndTime());
        assertEquals(60, reservation.getTotalCost());
        assertEquals(slot, reservation.getSlot());
        assertNotNull(reservation.getReservationToken());
    }
}
