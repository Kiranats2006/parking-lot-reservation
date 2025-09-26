package com.parkinglot.reservation.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkinglot.reservation.exception.ResourceNotFoundException;
import com.parkinglot.reservation.model.ParkingSlot;
import com.parkinglot.reservation.model.Reservation;
import com.parkinglot.reservation.repository.ParkingSlotRepository;
import com.parkinglot.reservation.repository.ReservationRepository;
import com.parkinglot.reservation.service.ReservationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationControllerTest {

    @InjectMocks
    private ReservationController reservationController;

    @Mock
    private ReservationService reservationService;

    @Mock
    private ParkingSlotRepository slotRepository;

    @Mock
    private ReservationRepository reservationRepository;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    private ParkingSlot slot;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();

        slot = new ParkingSlot("A1", "4-wheeler", null);
        reservation = new Reservation(
                "KA01AB1234",
                "4-wheeler",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2),
                60,
                slot
        );
    }

    // ---------------- Create Reservation ----------------
    @Test
    void testCreateReservation() throws Exception {
        when(slotRepository.findById(1L)).thenReturn(Optional.of(slot));
        when(reservationService.createReservation(anyString(), anyString(), any(), any(), any()))
                .thenReturn(reservation);

        mockMvc.perform(post("/reservations")
                        .param("vehicleNumber", "KA01AB1234")
                        .param("vehicleType", "4-wheeler")
                        .param("slotId", "1")
                        .param("startTime", LocalDateTime.now().toString())
                        .param("endTime", LocalDateTime.now().plusHours(2).toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateReservationSlotNotFound() throws Exception {
        when(slotRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            reservationController.createReservation(
                    "KA01AB1234", "4-wheeler", 1L,
                    LocalDateTime.now().toString(),
                    LocalDateTime.now().plusHours(2).toString());
        });

        assertEquals("Slot not found with id 1", thrown.getMessage());
    }

    // ---------------- Get Reservation By ID ----------------
    @Test
    void testGetReservationById() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        var response = reservationController.getReservationById(1L);
        assertEquals(reservation, response.getBody());
    }

    @Test
    void testGetReservationByIdNotFound() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            reservationController.getReservationById(1L);
        });
        assertEquals("Reservation not found with id 1", thrown.getMessage());
    }

    // ---------------- Delete Reservation ----------------
    @Test
    void testDeleteReservation() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        var response = reservationController.deleteReservation(1L);
        verify(reservationRepository, times(1)).delete(reservation);
        assertTrue(response.getBody().toString().contains("Reservation cancelled successfully"));
    }

    @Test
    void testDeleteReservationNotFound() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            reservationController.deleteReservation(1L);
        });
        assertEquals("Reservation not found with id 1", thrown.getMessage());
    }

    // ---------------- Get Availability ----------------
    @Test
    void testGetAvailabilityWithAvailableSlots() {
        List<ParkingSlot> allSlots = Arrays.asList(slot);
        when(slotRepository.findAll()).thenReturn(allSlots);
        when(reservationService.getAvailableSlots(any(), any(), any())).thenReturn(allSlots);

        var response = reservationController.getAvailability(
                LocalDateTime.now().toString(),
                LocalDateTime.now().plusHours(2).toString()
        );

        assertEquals(allSlots, response.getBody());
    }

    @Test
    void testGetAvailabilityNoSlots() {
        List<ParkingSlot> allSlots = Arrays.asList(slot);
        when(slotRepository.findAll()).thenReturn(allSlots);
        when(reservationService.getAvailableSlots(any(), any(), any())).thenReturn(Arrays.asList());

        var response = reservationController.getAvailability(
                LocalDateTime.now().toString(),
                LocalDateTime.now().plusHours(2).toString()
        );

        assertEquals("No slots available for the selected time. Check console for next available slots.", response.getBody());
    }
}
