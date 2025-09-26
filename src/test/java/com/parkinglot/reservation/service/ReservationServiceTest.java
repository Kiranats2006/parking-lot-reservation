package com.parkinglot.reservation.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import com.parkinglot.reservation.exception.InvalidRequestException;
import com.parkinglot.reservation.exception.SlotUnavailableException;
import com.parkinglot.reservation.model.ParkingSlot;
import com.parkinglot.reservation.model.Reservation;
import com.parkinglot.reservation.repository.ReservationRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    private ParkingSlot slot;
    private LocalDateTime start;
    private LocalDateTime end;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        slot = new ParkingSlot("A1", "4-wheeler", null);
        start = LocalDateTime.now();
        end = start.plusHours(2);
    }

    @Test
    void testCreateReservation_Normal() {
        Reservation saved = new Reservation("KA01AB1234", "4-wheeler", start, end, 60, slot);
        when(reservationRepository.findBySlotAndEndTimeAfterAndStartTimeBefore(slot, start, end))
            .thenReturn(Collections.emptyList());
        when(reservationRepository.save(any())).thenReturn(saved);

        Reservation result = reservationService.createReservation("KA01AB1234", "4-wheeler", start, end, slot);
        assertEquals("KA01AB1234", result.getVehicleNumber());
        assertEquals(slot, result.getSlot());
    }

    @Test
    void testCreateReservation_InvalidTime() {
        assertThrows(InvalidRequestException.class,
            () -> reservationService.createReservation("KA01AB1234", "4-wheeler", end, start, slot));
    }

    @Test
    void testCreateReservation_Exceeds24Hours() {
        LocalDateTime endLater = start.plusHours(25);
        assertThrows(InvalidRequestException.class,
            () -> reservationService.createReservation("KA01AB1234", "4-wheeler", start, endLater, slot));
    }

    @Test
    void testCreateReservation_InvalidVehicleNumber() {
        assertThrows(InvalidRequestException.class,
            () -> reservationService.createReservation("INVALID123", "4-wheeler", start, end, slot));
    }

    @Test
    void testCreateReservation_SlotUnavailable() {
        Reservation existing = new Reservation("KA02BC5678", "4-wheeler", start, end, 60, slot);
        when(reservationRepository.findBySlotAndEndTimeAfterAndStartTimeBefore(slot, start, end))
            .thenReturn(List.of(existing));

        assertThrows(SlotUnavailableException.class,
            () -> reservationService.createReservation("KA01AB1234", "4-wheeler", start, end, slot));
    }

    @Test
    void testGetAvailableSlots_Normal() {
        when(reservationRepository.findBySlotAndEndTimeAfterAndStartTimeBefore(slot, start, end))
            .thenReturn(Collections.emptyList());

        List<ParkingSlot> slots = reservationService.getAvailableSlots(start, end, List.of(slot));
        assertEquals(1, slots.size());
    }

    @Test
    void testGetAvailableSlots_NoAvailable() {
        Reservation existing = new Reservation("KA01AB1234", "4-wheeler", start, end, 60, slot);
        when(reservationRepository.findBySlotAndEndTimeAfterAndStartTimeBefore(slot, start, end))
            .thenReturn(List.of(existing));

        List<ParkingSlot> slots = reservationService.getAvailableSlots(start, end, List.of(slot));
        assertTrue(slots.isEmpty());
    }

    @Test
    void testGetAvailableSlots_InvalidTime() {
        assertThrows(InvalidRequestException.class,
            () -> reservationService.getAvailableSlots(end, start, List.of(slot)));
    }

    @Test
    void testCreateReservation_HoursRoundedUp() {
        // 2 hours 30 minutes duration
        LocalDateTime endNonWhole = start.plusHours(2).plusMinutes(30);
        
        Reservation saved = new Reservation("KA01AB1234", "4-wheeler", start, endNonWhole, 90, slot);
        when(reservationRepository.findBySlotAndEndTimeAfterAndStartTimeBefore(slot, start, endNonWhole))
            .thenReturn(Collections.emptyList());
        when(reservationRepository.save(any())).thenReturn(saved);

        Reservation result = reservationService.createReservation("KA01AB1234", "4-wheeler", start, endNonWhole, slot);

        // 2.5 hours → rounds up to 3 hours, rate 30 → 3*30 = 90
        assertEquals(90, result.getTotalCost());
    }

}
