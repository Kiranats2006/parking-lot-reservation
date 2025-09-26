package com.parkinglot.reservation.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import com.parkinglot.reservation.model.ParkingSlot;
import com.parkinglot.reservation.model.Reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ParkingSlotRepository slotRepository;

    private ParkingSlot slot;
    private Reservation reservation1;
    private Reservation reservation2;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    void setUp() {
        // Create and save slot
        slot = new ParkingSlot("A1", "4-wheeler", null);
        slotRepository.save(slot);

        // Define reservation times
        startTime = LocalDateTime.now();
        endTime = startTime.plusHours(2);

        // Create overlapping reservations
        reservation1 = new Reservation("KA01AB1234", "4-wheeler", startTime, endTime, 60, slot);
        reservation2 = new Reservation("KA02BC5678", "4-wheeler", startTime.plusMinutes(30), endTime.plusMinutes(30), 60, slot);

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);
    }

    @Test
    void testFindBySlotAndEndTimeAfterAndStartTimeBefore_overlap() {
        LocalDateTime queryStart = startTime.plusMinutes(15);
        LocalDateTime queryEnd = endTime.plusMinutes(15);

        List<Reservation> results = reservationRepository
                .findBySlotAndEndTimeAfterAndStartTimeBefore(slot, queryStart, queryEnd);

        assertNotNull(results);
        assertEquals(2, results.size(), "Should return both overlapping reservations");

        // Assert using reservationToken to avoid object equality issues
        assertTrue(results.stream().anyMatch(r -> r.getReservationToken().equals(reservation1.getReservationToken())));
        assertTrue(results.stream().anyMatch(r -> r.getReservationToken().equals(reservation2.getReservationToken())));
    }

    @Test
    void testFindBySlotAndEndTimeAfterAndStartTimeBefore_noOverlap() {
        LocalDateTime queryStart = endTime.plusHours(1);
        LocalDateTime queryEnd = endTime.plusHours(2);

        List<Reservation> results = reservationRepository
                .findBySlotAndEndTimeAfterAndStartTimeBefore(slot, queryStart, queryEnd);

        assertNotNull(results);
        assertTrue(results.isEmpty(), "Should return empty list when no reservations overlap");
    }

    @Test
    void testFindBySlotAndEndTimeAfterAndStartTimeBefore_partialOverlap() {
        LocalDateTime queryStart = startTime.minusMinutes(30);
        LocalDateTime queryEnd = startTime.plusMinutes(15);

        List<Reservation> results = reservationRepository
                .findBySlotAndEndTimeAfterAndStartTimeBefore(slot, queryStart, queryEnd);

        assertNotNull(results);
        assertEquals(1, results.size(), "Should return only reservation1 for partial overlap");
        assertEquals(reservation1.getReservationToken(), results.get(0).getReservationToken());
    }
}
